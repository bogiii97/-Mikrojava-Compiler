package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;


import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	Logger log = Logger.getLogger(getClass());
	
	private boolean errorDetected = false;
	private Obj mainMethod = null;
	private Struct currVarType = null;
	private Struct currConstType = null;
	private int nVars;

	public Obj getMainMethodObj() {
		return mainMethod;
	}
	
	public int getnVars() {
		return nVars;
	}
	
	private void printObjNode(Obj o) {
		StringBuilder sb = new StringBuilder();
		sb.append("Objektni čvor: ");
		sb.append("Kind: " + o.getKind() + ", ");
		sb.append("Name: " + o.getName() + ", ");
		sb.append("Adr: " + o.getAdr() + ", ");
		sb.append("Level: " + o.getLevel());
		report_info(sb.toString(),null);
	}
	
	private boolean existInCurrentScope(String name, int line) {
		Obj o = MyTab.currentScope().findSymbol(name);
		if(o != null) {
			report_error("Već je deklarisano ime " + name + " u MyTabeli simbola! " + "Greška na liniji " + line, null);
			return true;
		}
		else return false;
	}
	
	private Struct convertMatToArrStruct(Struct s) {
		if(s == MyTab.intMatType) {
			return MyTab.intArrayType;
		} else if(s == MyTab.boolMatType) {
			return MyTab.boolArrayType;
		}
		else if (s == MyTab.charMatType) {
			return MyTab.charArrayType;
		}
		return MyTab.noType;
	}
	
	private Struct convertMatToVarStruct(Struct s) {
		if(s == MyTab.intMatType) {
			return MyTab.intType;
		} else if(s == MyTab.boolMatType) {
			return MyTab.boolType;
		}
		else if (s == MyTab.charMatType) {
			return MyTab.charType;
		}
		return MyTab.noType;
	}
	
	private Struct convertArrToVarStruct(Struct s) {
		if(s == MyTab.intArrayType) {
			return MyTab.intType;
		} else if(s == MyTab.boolArrayType) {
			return MyTab.boolType;
		}
		else if (s == MyTab.charArrayType) {
			return MyTab.charType;
		}
		return MyTab.noType;
	}
	
	private Struct convertVarToArrStruct(Struct s) {
		if(s == MyTab.intType) {
			return MyTab.intArrayType;
		} else if(s == MyTab.boolType) {
			return MyTab.boolArrayType;
		}
		else if (s == MyTab.charType) {
			return MyTab.charArrayType;
		}
		return MyTab.noType;
	}
	
	private Struct convertVarToMatStruct(Struct s) {
		if(s == MyTab.intType) {
			return MyTab.intMatType;
		} else if(s == MyTab.boolType) {
			return MyTab.boolMatType;
		}
		else if (s == MyTab.charType) {
			return MyTab.charMatType;
		}
		return MyTab.noType;
	}
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean getErrorDetected(){
    	return !errorDetected;
    }
	
	

	public void visit(ProgName progName){
    	progName.obj = MyTab.insert(Obj.Prog, progName.getProgName(), MyTab.noType);
    	MyTab.openScope();
    }
	
	public void visit(ProgramWithMain programWithMain) {
		nVars = Tab.currentScope.getnVars();
		MyTab.chainLocalSymbols(programWithMain.getProgName().obj);
    	MyTab.closeScope();
	}
	
	public void visit(ProgramWithoutMain programWithoutMain) {
		nVars = Tab.currentScope.getnVars();
		MyTab.chainLocalSymbols(programWithoutMain.getProgName().obj);
    	MyTab.closeScope();
	}
	
	public void visit(MethodDecl methodDecl) {
		MyTab.chainLocalSymbols(mainMethod);
    	MyTab.closeScope();
	}
	
	public void visit(MethodName methodName) {
		if(!methodName.getMethName().equals("main")) {
			report_error("Ime funkcije mora biti main! Greška na liniji " + methodName.getLine(), null);
		}
		else if (!methodName.getMethType().equals("void")) {
			report_error("Povratni tip funkcije main mora biti void! Greška na liniji " + methodName.getLine(), null);
		}
		mainMethod = MyTab.insert(Obj.Meth, methodName.getMethName(), MyTab.noType);
		methodName.obj = mainMethod;
		MyTab.openScope();
		report_info("Obradjuje se funkcija " + methodName.getMethName(), methodName);
		
	}
	
	public void visit(Type type) {
		Obj typeNode = MyTab.find(type.getType());
    	if(typeNode == MyTab.noObj){
    		report_error("Nije pronadjen tip " + type.getType() + " u MyTabeli simbola! Greška na liniji " + type.getLine(), null);
    		type.struct = MyTab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + type.getType() + " ne predstavlja tip! Greška na liniji " + type.getLine(), type);
    			type.struct = MyTab.noType;
    		}
    	}
    	currVarType = type.struct;
    	currConstType = type.struct;
	}
	
	public void visit(VarName varName) {
		if(!existInCurrentScope(varName.getVarName(), varName.getLine())) {
			Obj var = MyTab.insert(Obj.Var, varName.getVarName(), currVarType);
			report_info("Deklarisana promenljiva " + "'" + varName.getVarName() + "'" + " na liniji " + varName.getLine(), null);
			printObjNode(var);
		}
	}
	
	public void visit(ArrName arrName) {
		if(!existInCurrentScope(arrName.getArrName(), arrName.getLine())) {
			report_info("Deklarisan niz " + "'"  + arrName.getArrName() + "'" + " na liniji " + arrName.getLine(), null);
			Struct s = null;
			switch(currVarType.getKind()) {
			case Struct.Int:
				s = MyTab.intArrayType;
				break;
			case Struct.Char:
				s = MyTab.charArrayType;
				break;
			case Struct.Bool:
				s = MyTab.boolArrayType;
				break;
			default:
				s = MyTab.noType;
				break;
			}
			Obj arr = MyTab.insert(Obj.Var, arrName.getArrName(), s);
			printObjNode(arr);
		}
	}
	
	public void visit(MatName matName) {
		if(!existInCurrentScope(matName.getMatName(), matName.getLine())) {
			report_info("Deklarisana matrica "+ "'" + matName.getMatName() + "'" + " na liniji " + matName.getLine(), null);
			
			Struct s = null;
			switch(currVarType.getKind()) {
			case Struct.Int:
				s = MyTab.intMatType;
				break;
			case Struct.Char:
				s = MyTab.charMatType;
				break;
			case Struct.Bool:
				s = MyTab.boolMatType;
				break;
			default:
				s = MyTab.noType;
				break;
			}
			Obj mat = MyTab.insert(Obj.Var, matName.getMatName(), s);
			printObjNode(mat);
		}
	}
	
	public void visit(ConstNumber constNumber) {
		if(!existInCurrentScope(constNumber.getNumber(), constNumber.getLine())) {
			report_info("Deklarisana konstanta " + "'" + constNumber.getNumber() + "'" + " na liniji " + constNumber.getLine(), null);
			Obj constNum = MyTab.insert(Obj.Con, constNumber.getNumber(), currConstType);
			constNum.setAdr(constNumber.getN());
			printObjNode(constNum);
		}
	}
	
	public void visit(ConstChar constChar) {
		if(!existInCurrentScope(constChar.getCharacter(), constChar.getLine())) {
			report_info("Deklarisana konstanta " + "'" + constChar.getCharacter() + "'" + " na liniji " + constChar.getLine(), null);
			Obj constCharacter = MyTab.insert(Obj.Con, constChar.getCharacter(), currConstType);
			constCharacter.setAdr(constChar.getC());
			printObjNode(constCharacter);
		}
	}
	
	public void visit(ConstBoolean constBoolean) {
		if(!existInCurrentScope(constBoolean.getBolean(), constBoolean.getLine())) {
			report_info("Deklarisana konstanta " + "'" + constBoolean.getBolean() + "'" + " na liniji " + constBoolean.getLine(), null);
			Obj constBool = MyTab.insert(Obj.Con, constBoolean.getBolean(), currConstType);
			constBool.setAdr(constBoolean.getB());
			printObjNode(constBool);
		}
	}
	
	public void visit(ReadStmt readStmt) {
		if(readStmt.getDesignator().struct != MyTab.intType && readStmt.getDesignator().struct != MyTab.boolType && readStmt.getDesignator().struct != MyTab.charType) {
			report_error("Parametar read funkcije mora biti tipa int, char ili bool! Greška na liniji " + readStmt.getLine(), null);
		}
	}
	
	public void visit(PrintOneStmt printOneStmt) {
		if(printOneStmt.getExpr().struct != MyTab.intType && printOneStmt.getExpr().struct != MyTab.boolType && printOneStmt.getExpr().struct != MyTab.charType) {
			report_error("Parametar print funkcije sa jednim parametrom mora biti tipa int, char ili bool! Greška na liniji " + printOneStmt.getLine(), null);
		}
	}
	
	public void visit(PrintDoubleStmt printDoubleStmt) {
		if(printDoubleStmt.getExpr().struct != MyTab.intArrayType && printDoubleStmt.getExpr().struct != MyTab.boolArrayType && printDoubleStmt.getExpr().struct != MyTab.charArrayType) {
			report_error("Parametar print funkcije sa dva parametra mora biti nizovskog tipa! Greška na liniji " + printDoubleStmt.getLine(), null);
		}
	}
	
	public void visit(EqualStmt equalStmt) {
		if(!equalStmt.getExpr().struct.assignableTo(equalStmt.getDesignator().struct))
    		report_error("Nekompatibilna dodela vrednosti! Greška na liniji " + equalStmt.getLine(), null);
	}
	
	public void visit(IncStmt incStmt) {
		Struct t = incStmt.getDesignator().struct;
		if(t != Tab.intType) {
			report_error("++ se primenjuje samo nad nečim što je tipa int! Greška na liniji "+ incStmt.getLine(), null);
		}
	}
	
	public void visit(DecStmt decStmt) {
		Struct t = decStmt.getDesignator().struct;
		if(t != Tab.intType) {
			report_error("-- se primenjuje samo nad nečim što je tipa int! Greška na liniji "+ decStmt.getLine(), null);
		}
	}
	
	public void visit(Expr expr) {
		expr.struct = expr.getAddopTermList().struct;
	}
	
	public void visit(AddopTerms addopTerms) {
		Struct te = addopTerms.getAddopTermList().struct;
    	Struct t = addopTerms.getTerm().struct;
    	if(te.equals(t) && te == Tab.intType){
    		addopTerms.struct = te;
    	}else{
    		report_error("Nekompatibilni tipovi u izrazu pri operaciji sabiranja! Greška na liniji "+ addopTerms.getLine(), null);
			addopTerms.struct = Tab.noType;
    	}
	}
	
	public void visit(MinopTerms minopTerms) {
		Struct te = minopTerms.getAddopTermList().struct;
    	Struct t = minopTerms.getTerm().struct;
    	if(te.equals(t) && te == Tab.intType){
    		minopTerms.struct = te;
    	}else{
    		report_error("Nekompatibilni tipovi u izrazu pri operaciji oduzimanja! Greška na liniji "+ minopTerms.getLine(), null);
    		minopTerms.struct = Tab.noType;
    	}
	}
	
	public void visit(MinusTerm minusTerm) {
		Struct t = minusTerm.getTerm().struct;
		if(t == Tab.intType) {
			minusTerm.struct = t;
		} else {
			report_error("Nakon minusa mora da usledi nešto što je tipa int! Greška na liniji "+ minusTerm.getLine(), null);
			minusTerm.struct = Tab.noType;
		}
	}
	
	public void visit(OneTerm oneTerm) {
		oneTerm.struct = oneTerm.getTerm().struct;
	}
	
	public void visit(MulopFactors mulopFactors) {
		Struct te = mulopFactors.getFactor().struct;
    	Struct t = mulopFactors.getTerm().struct;
    	if(te.equals(t) && te == Tab.intType){
    		mulopFactors.struct = te;
    	}else{
			report_error("Nekompatibilni tipovi u izrazu pri mulop operacijama! Greška na liniji "+ mulopFactors.getLine(), null);
			mulopFactors.struct = Tab.noType;
    	}
	}
	
	public void visit(OneFactor oneFactor) {
		oneFactor.struct = oneFactor.getFactor().struct;
	}
	
	public void visit(NumberFactor numberFactor) {
		numberFactor.struct = MyTab.intType;
	}
	
	public void visit(CharFactor charFactor) {
		charFactor.struct = MyTab.charType;
	}
	
	public void visit(ExprFactor exprFactor) {
		exprFactor.struct = exprFactor.getExpr().struct;
	}		
	
	public void visit(BoolFactor boolFactor) {
		boolFactor.struct = MyTab.boolType;
	}
	
	public void visit(DesignatorFactor designatorFactor) {
		designatorFactor.struct = designatorFactor.getDesignator().struct;
	}
	
	public void visit(NewFactorArr newFactorArr) {
		if(newFactorArr.getExpr().struct != MyTab.intType) {
			report_error("Izraz u [] mora biti tipa int! Greška na liniji " + newFactorArr.getLine(), null);
		}
		newFactorArr.struct = convertVarToArrStruct(newFactorArr.getType().struct);
	}
	
	public void visit(NewFactorMat newFactorMat) {
		if(newFactorMat.getExpr().struct != MyTab.intType || newFactorMat.getExpr1().struct != MyTab.intType) {
			report_error("Izraz u obe [] mora biti tipa int! Greška na liniji " + newFactorMat.getLine(), null);
		}
		newFactorMat.struct = convertVarToMatStruct(newFactorMat.getType().struct);
	}

	

	public void visit(VarDesignator varDesignator) {
		Obj var = MyTab.find(varDesignator.getVar());
		varDesignator.struct = MyTab.noType;
		if (var == MyTab.noObj) {
			report_error("Ime " + varDesignator.getVar() + " nije nigde deklarisano! Greška na liniji " + varDesignator.getLine() , null);
		}
		else {
			if(var.getKind() != Obj.Var && var.getKind() != Obj.Con ) {
				report_error("Ime " + varDesignator.getVar() + " ne predstavlja ni promenljivu ni konstantu u tabeli simbola! Greška na liniji " + varDesignator.getLine(), null);
			}
			else {
				if(var.getKind() == Obj.Con) {
					report_info("Detektovana konstanta " + "'" + varDesignator.getVar() + "'" + " na liniji " + varDesignator.getLine(), null);
				}
				else if (var.getKind() == Obj.Var) {
					report_info("Detektovana promenljiva " + "'" + varDesignator.getVar() + "'" + " na liniji " + varDesignator.getLine(), null);
				}
				varDesignator.struct = var.getType();
				printObjNode(var);
			}
		}
	}
	
	public void visit(ArrDesignator arrDesignator) {
		Obj arr = MyTab.find(arrDesignator.getDesignatorName().getName());
		arrDesignator.struct = MyTab.noType;
		if(arr == MyTab.noObj) {
			report_error("Ime " + arrDesignator.getDesignatorName().getName() + " nije nigde deklarisano! Greška na liniji " + arrDesignator.getLine(), null);
		}
		else {
			if(arr.getKind() != Obj.Var ) {
				report_error("Ime " + arrDesignator.getDesignatorName().getName() + " ne predstavlja promenljivu u tabeli simbola! Greška na liniji " + arrDesignator.getLine(), null);
			}
			else {
				if(arr.getType() != MyTab.intArrayType && arr.getType() != MyTab.boolArrayType && arr.getType() != MyTab.charArrayType && arr.getType() != MyTab.intMatType && arr.getType() != MyTab.boolMatType && arr.getType() != MyTab.charMatType) {
					report_error("Tip promenljive " + arrDesignator.getDesignatorName().getName() + " nije niz integer-a, bool-ova ili char-ova! Greška na liniji " + arrDesignator.getLine(), null);
				}
				else {
					if(arrDesignator.getExpr().struct.getKind() != Struct.Int) {
						report_error("Indeks niza mora biti tipa int! Greška na liniji " + arrDesignator.getLine(), null);
					}
					else {
						if(arr.getType() == MyTab.intArrayType || arr.getType() == MyTab.boolArrayType || arr.getType() == MyTab.charArrayType) {
							arrDesignator.struct =  convertArrToVarStruct(arr.getType()); 
						}
						else if(arr.getType() == MyTab.intMatType || arr.getType() == MyTab.boolMatType || arr.getType() == MyTab.charMatType) {
							arrDesignator.struct = convertMatToArrStruct(arr.getType());
						}
						printObjNode(arr);
						report_info("Detektovan niz " + "'" + arrDesignator.getDesignatorName().getName() + "'" + " na liniji " + arrDesignator.getLine(), null);	
					}
				}
			}
		}
	}
	
	public void visit(MatDesignator matDesignator) {
		Obj mat = MyTab.find(matDesignator.getDesignatorName().getName());
		matDesignator.struct = MyTab.noType;
		if(mat == MyTab.noObj) {
			report_error("Ime " + matDesignator.getDesignatorName().getName() + " nije nigde deklarisano! Greška na liniji " + matDesignator.getLine() , null);
		}
		else {
			if(mat.getKind() != Obj.Var ) {
				report_error("Ime " + matDesignator.getDesignatorName().getName() + " ne predstavlja promenljivu u tabeli simbola! Greška na liniji " + matDesignator.getLine(), null);
			}
			else {
				if(mat.getType() != MyTab.intMatType && mat.getType() != MyTab.boolMatType && mat.getType() != MyTab.charMatType) {
					report_error("Tip promenljive " + matDesignator.getDesignatorName().getName() + " nije matrica integer-a, bool-ova ili char-ova! Greška na liniji " + matDesignator.getLine(), null);
				}
				else {
					if(matDesignator.getSecondBrackets().getExpr().struct.getKind() != Struct.Int || matDesignator.getFirstBrackets().getExpr().struct.getKind() != Struct.Int) {
						report_error("Indeksi matrice moraju biti tipa int! Greška na liniji " + matDesignator.getLine(), null);
					}
					else {
						matDesignator.struct = convertMatToVarStruct(mat.getType());
						printObjNode(mat);
						report_info("Detektovana matrica " + "'" + matDesignator.getDesignatorName().getName() + "'" + " na liniji " + matDesignator.getLine(), null);
					}
				}
			}
		}
	}
}

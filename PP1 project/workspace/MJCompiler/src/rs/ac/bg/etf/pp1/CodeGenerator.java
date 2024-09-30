package rs.ac.bg.etf.pp1;

import java.util.Scanner;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.ac.bg.etf.pp1.util.S;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPc;
	
	public int getMainPc(){
		return mainPc;
	}
	
	private class VarCounter extends VisitorAdaptor{
		
		private int count = 0;
		
		public void visit(VarName varName){
			count++;
		}
		
		public void visit(ArrName arrName){
			count++;
		}
		
		public void visit(MatName matName){
			count++;
		}
		
		public int getCount() {
			return count;
		}
	}
	
	public void visit(MethodName methodName){
		if("main".equalsIgnoreCase(methodName.getMethName())){
			mainPc = Code.pc;
		}
		
		methodName.obj.setAdr(Code.pc);

		SyntaxNode methodNode = methodName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);


		Code.put(Code.enter);
		Code.put(0);
		Code.put(varCnt.getCount());
		
	}
	
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	public void visit(PrintOneStmt printOneStmt){
		if(printOneStmt.getExpr().struct == MyTab.charType){
			Code.loadConst(4);
			Code.put(Code.bprint);
		}else{
			Code.loadConst(4);
			Code.put(Code.print);
		}
	}
	
	public void visit(PrintDoubleStmt printDoubleStmt) {
		Code.loadConst(printDoubleStmt.getN2());
		Code.put(Code.aload);
		if(printDoubleStmt.getExpr().struct == MyTab.charType){
			Code.loadConst(4);
			Code.put(Code.bprint);
		}else{
			Code.loadConst(4);
			Code.put(Code.print);
		}
	}
	
	public void visit(ReadStmt readStmt) {
		/*int number = 0;
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Unesite vrednost: ");
	    String input = scanner.nextLine();
	        
	    
	    
	    if(readStmt.getDesignator().struct == MyTab.intType) {
	    	try {
	    		number = Integer.parseInt(input);
	    		Code.loadConst(number);
	    	} catch(NumberFormatException e) {
	    		System.out.println("Uneta vrednost nije validan broj.");
                scanner.close();
	    	}
	    }
	    else if (readStmt.getDesignator().struct == MyTab.boolType) {
	    	if (input.equalsIgnoreCase("true")) {
	            number = 1;
	            Code.loadConst(number);
	        } else if (input.equalsIgnoreCase("false")) {
	            number = 0;
	            Code.loadConst(number);
	        }
	        else {
	        	System.out.println("Uneta vrednost nije true ili false.");
                scanner.close();
	        }
	    }
	    else if(readStmt.getDesignator().struct == MyTab.charType) {
	    	if (input.length() == 1) {
	    		Character c = input.charAt(0);
	    		Code.loadConst(c);
	    	} else {
	    		System.out.println("Morate uneti samo jedan karakter");
	    	}
	    }*/
	    
	    if(readStmt.getDesignator().struct == MyTab.charType){
			Code.put(Code.bread);
		}
		else {
			Code.put(Code.read);
		}
	    
	    
		Designator d = readStmt.getDesignator();
		Obj o = null;
		if(d instanceof ArrDesignator) {
			o = MyTab.findObj(((ArrDesignator) d).getDesignatorName().getName());
		}
		else if(d instanceof VarDesignator) {
			o = MyTab.findObj(((VarDesignator) d).getVar());
		}
		else if(d instanceof MatDesignator) {
			o = MyTab.findObj(((MatDesignator) d).getDesignatorName().getName());
		}
		
		Code.store(o);
	}
	
	
	public void visit(NumberFactor numberFactor){
		Obj con = MyTab.insert(Obj.Con, "numConst", numberFactor.struct);
		con.setLevel(0);
		con.setAdr((int)numberFactor.getN1());
		Code.load(con);
	}
	
	public void visit(CharFactor charFactor){
		Obj con = MyTab.insert(Obj.Con, "charConst", charFactor.struct);
		con.setLevel(0);
		con.setAdr(charFactor.getC1());
		Code.load(con);
	}
	
	public void visit(BoolFactor boolFactor) {
		Obj con = MyTab.insert(Obj.Con, "boolConst", boolFactor.struct);
		con.setLevel(0);
		con.setAdr(boolFactor.getB1());
		Code.load(con);
	}
	
	public void visit(AddopTerms addopTerms) {
		Code.put(Code.add);
	}
	
	public void visit(MinopTerms minopTerms) {
		Code.put(Code.sub);
	}
	
	public void visit(MulopFactors mulopFactors) {
		if(mulopFactors.getMulop() instanceof Mul) {
			Code.put(Code.mul);
		} else if (mulopFactors.getMulop() instanceof Div) {
			Code.put(Code.div);
		} else if (mulopFactors.getMulop() instanceof Mod) {
			Code.put(Code.rem);
		}
	}
	
	public void visit(MinusTerm minusTerm) {
		Code.put(Code.neg);
	}
	
	public void visit(IncStmt incStmt) {
		Designator d = incStmt.getDesignator();
		if(d instanceof VarDesignator) {
			Obj o = MyTab.findObj(((VarDesignator)d).getVar());
			if(o != MyTab.noObj) {
				Code.load(o);
				Code.loadConst(1);
				Code.put(Code.add);
				Code.store(o);
			}
		} else if(d instanceof ArrDesignator) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.astore);
		} else if (d instanceof MatDesignator) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.add);
			Code.put(Code.astore);
		}
	}
	
	public void visit(DecStmt decStmt) {
		Designator d = decStmt.getDesignator();
		if(d instanceof VarDesignator) {
			Obj o = MyTab.findObj(((VarDesignator)d).getVar());
			if(o != MyTab.noObj) {
				Code.load(o);
				Code.loadConst(1);
				Code.put(Code.neg);
				Code.put(Code.add);
				Code.store(o);
			}
		} else if(d instanceof ArrDesignator) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.neg);
			Code.put(Code.add);
			Code.put(Code.astore);
		} else if (d instanceof MatDesignator) {
			Code.put(Code.dup2);
			Code.put(Code.aload);
			Code.loadConst(1);
			Code.put(Code.neg);
			Code.put(Code.add);
			Code.put(Code.astore);
		}
	}
	
	public void visit(DesignatorFactor designatorFactor) {
		Designator d = designatorFactor.getDesignator();
		Obj o = null;
		if(d instanceof VarDesignator) {
			o = MyTab.findObj(((VarDesignator) d).getVar());
			Code.load(o);
		}
		else if (d instanceof ArrDesignator) {
			o = MyTab.findObj(((ArrDesignator) d).getDesignatorName().getName());
			Code.put(Code.aload);
		}
		else if (d instanceof MatDesignator) {
			o = MyTab.findObj(((MatDesignator) d).getDesignatorName().getName());
			Code.put(Code.aload);
		}
	}
	
	public void visit(EqualStmt equalStmt) {
		Designator d = equalStmt.getDesignator();
		Obj o = null;
		if(d instanceof VarDesignator) {
			o = MyTab.findObj(((VarDesignator) d).getVar());
			Code.store(o);
		}
		else if (d instanceof ArrDesignator) {
			o = MyTab.findObj(((ArrDesignator) d).getDesignatorName().getName());
			Code.put(Code.astore);
		}
		else if (d instanceof MatDesignator) {
			o = MyTab.findObj(((MatDesignator) d).getDesignatorName().getName());
			Code.put(Code.astore);
		}
	}
	
	public void visit(DesignatorName designatorName) {
		Obj o = MyTab.findObj(designatorName.getName());
		Code.load(o);
	}
	
	public void visit(NewFactorArr newFactorArr) {
		Code.put(Code.newarray);
		Code.put(1); //Moze i 0 	DORADITI
	}

	public void visit(NewFactorMat newFactorMat) {
		//3 5  
		//0 r2 new sw3 . d3 new ast sw2 inc sw2 d3 p sw2 len jccne adr-pc p p
		Code.put(Code.dup); //3 5 5
		Code.put(Code.dup_x2); //5 3 5 5
		Code.put(Code.pop); //5 3 5
		Code.put(Code.mul); //5 15
		Code.loadConst(1); //5 15 1
		Code.put(Code.add); // 5 16
		Code.put(Code.newarray); 
		Code.put(1); // 5 arr
		Code.put(Code.dup_x1); //arr 5 arr
		Code.put(Code.dup_x1); //arr arr 5 arr
		Code.put(Code.arraylength); //arr arr 5 16
		Code.loadConst(1); //arr arr  5 16 1
		Code.put(Code.sub); //arr arr 5 15
		Code.put(Code.dup_x1); //arr arr 15 5 15
		Code.put(Code.pop); //arr arr 15 5
		Code.put(Code.astore); //arr
		
	}
	
	public void visit(MatDesignator matDesignator) {
		Code.put(Code.add);
	}
	
	public void visit(FirstBrackets firstBrackets) {
		//a 2  
		Code.put(Code.dup_x1); //2 a 2
		Code.put(Code.pop); //2 a
		Code.put(Code.dup); //2 a a
		Code.put(Code.dup); //2 a a a
		Code.put(Code.arraylength); //2 a a len(a)
		Code.put(Code.const_1); //2 a a len(a) 1
		Code.put(Code.sub); //2 a a len(a)-1
		Code.put(Code.aload); //2 a a[len(a)-1]
		Code.put(Code.dup_x2); //a[len(a)-1] 2 a a[len(a)-1]
		Code.put(Code.pop); //a[len(a)-1] 2 a
		Code.put(Code.dup_x2); //a a[len(a)-1] 2 a
		Code.put(Code.pop); //a a[len(a)-1] 2
		Code.put(Code.mul); //a a[len(a)-1]*2
	}
	
}

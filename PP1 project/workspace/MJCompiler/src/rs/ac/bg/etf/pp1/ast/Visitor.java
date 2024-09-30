// generated with ast extension for cup
// version 0.8
// 26/7/2024 13:1:7


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Designator Designator);
    public void visit(AddopTermList AddopTermList);
    public void visit(Factor Factor);
    public void visit(Assignop Assignop);
    public void visit(Mulop Mulop);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ConstDecl ConstDecl);
    public void visit(SingleVar SingleVar);
    public void visit(StatementAlt StatementAlt);
    public void visit(VarList VarList);
    public void visit(SingleConst SingleConst);
    public void visit(StaticDesignator StaticDesignator);
    public void visit(VarDeclList VarDeclList);
    public void visit(VarDecl VarDecl);
    public void visit(ConstDeclList ConstDeclList);
    public void visit(DeclList DeclList);
    public void visit(Statement Statement);
    public void visit(Term Term);
    public void visit(Program Program);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(StatementList StatementList);
    public void visit(Mod Mod);
    public void visit(Div Div);
    public void visit(Mul Mul);
    public void visit(Equal Equal);
    public void visit(DesignatorName DesignatorName);
    public void visit(SecondBrackets SecondBrackets);
    public void visit(FirstBrackets FirstBrackets);
    public void visit(MatDesignator MatDesignator);
    public void visit(VarDesignator VarDesignator);
    public void visit(ArrDesignator ArrDesignator);
    public void visit(NewFactorMat NewFactorMat);
    public void visit(NewFactorArr NewFactorArr);
    public void visit(DesignatorFactor DesignatorFactor);
    public void visit(BoolFactor BoolFactor);
    public void visit(ExprFactor ExprFactor);
    public void visit(CharFactor CharFactor);
    public void visit(NumberFactor NumberFactor);
    public void visit(MulopFactors MulopFactors);
    public void visit(OneFactor OneFactor);
    public void visit(MinopTerms MinopTerms);
    public void visit(AddopTerms AddopTerms);
    public void visit(MinusTerm MinusTerm);
    public void visit(OneTerm OneTerm);
    public void visit(Expr Expr);
    public void visit(DecStmt DecStmt);
    public void visit(IncStmt IncStmt);
    public void visit(EqualStmt EqualStmt);
    public void visit(PrintDoubleStmt PrintDoubleStmt);
    public void visit(PrintOneStmt PrintOneStmt);
    public void visit(ReadStmt ReadStmt);
    public void visit(DesignatorStmt DesignatorStmt);
    public void visit(StatementDerived1 StatementDerived1);
    public void visit(Stmt Stmt);
    public void visit(NoStmtList NoStmtList);
    public void visit(StmtList StmtList);
    public void visit(Type Type);
    public void visit(NoDeclarationlList NoDeclarationlList);
    public void visit(DeclarationList DeclarationList);
    public void visit(MatName MatName);
    public void visit(ArrName ArrName);
    public void visit(VarName VarName);
    public void visit(OneVar OneVar);
    public void visit(Vars Vars);
    public void visit(VarDeclDerived2 VarDeclDerived2);
    public void visit(VarDeclDerived1 VarDeclDerived1);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ConstBoolean ConstBoolean);
    public void visit(ConstChar ConstChar);
    public void visit(ConstNumber ConstNumber);
    public void visit(OneConst OneConst);
    public void visit(Consts Consts);
    public void visit(ConstDeclNumber ConstDeclNumber);
    public void visit(NoDeclList NoDeclList);
    public void visit(DeclListVar DeclListVar);
    public void visit(DeclListConst DeclListConst);
    public void visit(MethodName MethodName);
    public void visit(MethodDecl MethodDecl);
    public void visit(ProgName ProgName);
    public void visit(ProgramWithoutMain ProgramWithoutMain);
    public void visit(ProgramWithMain ProgramWithMain);

}

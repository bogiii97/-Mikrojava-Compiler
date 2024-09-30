// generated with ast extension for cup
// version 0.8
// 26/7/2024 13:1:7


package rs.ac.bg.etf.pp1.ast;

public class Stmt extends Statement {

    private StatementAlt StatementAlt;

    public Stmt (StatementAlt StatementAlt) {
        this.StatementAlt=StatementAlt;
        if(StatementAlt!=null) StatementAlt.setParent(this);
    }

    public StatementAlt getStatementAlt() {
        return StatementAlt;
    }

    public void setStatementAlt(StatementAlt StatementAlt) {
        this.StatementAlt=StatementAlt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementAlt!=null) StatementAlt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementAlt!=null) StatementAlt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementAlt!=null) StatementAlt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Stmt(\n");

        if(StatementAlt!=null)
            buffer.append(StatementAlt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Stmt]");
        return buffer.toString();
    }
}

// generated with ast extension for cup
// version 0.8
// 26/7/2024 13:1:7


package rs.ac.bg.etf.pp1.ast;

public class MatName extends SingleVar {

    private String matName;

    public MatName (String matName) {
        this.matName=matName;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName=matName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatName(\n");

        buffer.append(" "+tab+matName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatName]");
        return buffer.toString();
    }
}
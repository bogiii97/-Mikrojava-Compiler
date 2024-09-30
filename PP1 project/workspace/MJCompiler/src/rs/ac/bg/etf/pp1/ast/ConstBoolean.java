// generated with ast extension for cup
// version 0.8
// 26/7/2024 13:1:7


package rs.ac.bg.etf.pp1.ast;

public class ConstBoolean extends SingleConst {

    private String bolean;
    private Integer b;

    public ConstBoolean (String bolean, Integer b) {
        this.bolean=bolean;
        this.b=b;
    }

    public String getBolean() {
        return bolean;
    }

    public void setBolean(String bolean) {
        this.bolean=bolean;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b=b;
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
        buffer.append("ConstBoolean(\n");

        buffer.append(" "+tab+bolean);
        buffer.append("\n");

        buffer.append(" "+tab+b);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstBoolean]");
        return buffer.toString();
    }
}

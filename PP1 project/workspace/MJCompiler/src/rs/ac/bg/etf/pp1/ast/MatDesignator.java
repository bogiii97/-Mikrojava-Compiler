// generated with ast extension for cup
// version 0.8
// 26/7/2024 13:1:7


package rs.ac.bg.etf.pp1.ast;

public class MatDesignator extends Designator {

    private DesignatorName DesignatorName;
    private FirstBrackets FirstBrackets;
    private SecondBrackets SecondBrackets;

    public MatDesignator (DesignatorName DesignatorName, FirstBrackets FirstBrackets, SecondBrackets SecondBrackets) {
        this.DesignatorName=DesignatorName;
        if(DesignatorName!=null) DesignatorName.setParent(this);
        this.FirstBrackets=FirstBrackets;
        if(FirstBrackets!=null) FirstBrackets.setParent(this);
        this.SecondBrackets=SecondBrackets;
        if(SecondBrackets!=null) SecondBrackets.setParent(this);
    }

    public DesignatorName getDesignatorName() {
        return DesignatorName;
    }

    public void setDesignatorName(DesignatorName DesignatorName) {
        this.DesignatorName=DesignatorName;
    }

    public FirstBrackets getFirstBrackets() {
        return FirstBrackets;
    }

    public void setFirstBrackets(FirstBrackets FirstBrackets) {
        this.FirstBrackets=FirstBrackets;
    }

    public SecondBrackets getSecondBrackets() {
        return SecondBrackets;
    }

    public void setSecondBrackets(SecondBrackets SecondBrackets) {
        this.SecondBrackets=SecondBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorName!=null) DesignatorName.accept(visitor);
        if(FirstBrackets!=null) FirstBrackets.accept(visitor);
        if(SecondBrackets!=null) SecondBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorName!=null) DesignatorName.traverseTopDown(visitor);
        if(FirstBrackets!=null) FirstBrackets.traverseTopDown(visitor);
        if(SecondBrackets!=null) SecondBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorName!=null) DesignatorName.traverseBottomUp(visitor);
        if(FirstBrackets!=null) FirstBrackets.traverseBottomUp(visitor);
        if(SecondBrackets!=null) SecondBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MatDesignator(\n");

        if(DesignatorName!=null)
            buffer.append(DesignatorName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FirstBrackets!=null)
            buffer.append(FirstBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SecondBrackets!=null)
            buffer.append(SecondBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatDesignator]");
        return buffer.toString();
    }
}

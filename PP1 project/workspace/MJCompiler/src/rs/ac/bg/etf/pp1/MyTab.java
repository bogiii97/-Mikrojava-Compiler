package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MyTab extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct intArrayType = new Struct(Struct.Array, intType);
	public static final Struct boolArrayType = new Struct(Struct.Array, boolType);
	public static final Struct charArrayType = new Struct(Struct.Array, charType);
	
	public static final Struct intMatType = new Struct(Struct.Array, new Struct(Struct.Array, intType));
	public static final Struct charMatType = new Struct(Struct.Array, new Struct(Struct.Array, charType));
	public static final Struct boolMatType = new Struct(Struct.Array, new Struct(Struct.Array, boolType));
	
	
	public static Obj findObj(String name) {
		Obj resultObj = null;
		Obj curr = null;
		for (Scope s = currentScope; s != null; s = s.getOuter()) {
			for(Obj o: s.getLocals().symbols()) {
				if(o.getKind() == resultObj.Prog) {
					curr = o;
					for(Obj p: curr.getLocalSymbols()) {
						if(p.getName().equals(name)) {
							resultObj = p;
							break;
						}
						if(p.getName().equals("main")) {
							for(Obj x: p.getLocalSymbols()) {
								if(x.getName().equals(name)) {
									resultObj = x;
									break;
								}
							}
						}
					}
				}
			}
		}
		return (resultObj != null) ? resultObj : noObj;
	}
}

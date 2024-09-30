package rs.ac.bg.etf.pp1.util;

import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;

public class S{
	
	//sw2 - x1 pop
	//sw3 - x2 pop sw2
	//dup3 - sw3 x2 sw2 x2 sw3 x2 sw3 sw2 sw3
	//rRot - x2 pop
	//rRot2 - rRot rRot
	
	public static void dup() {
		Code.put(Code.dup);
	}
	
	public static void dup2() {
		Code.put(Code.dup2);
	}
	
	public static void dup_x1() {
		Code.put(Code.dup_x1);
	}
	
	public static void dup_x2() {
		Code.put(Code.dup_x2);
	}
	
	public static void load(Obj o) {
		Code.load(o);
	}
	
	public static void loadConst(int x) {
		Code.loadConst(x);
	}
	
	public static void store(Obj o) {
		Code.store(o);
	}
	
	public static void add() {
		Code.put(Code.add);
	}
	
	public static void sub() {
		Code.put(Code.sub);
	}
	
	public static void mul() {
		Code.put(Code.mul);
	}
	
	public static void div() {
		Code.put(Code.div);
	}
	
	public static void rem() {
		Code.put(Code.rem);
	}
	
	public static void neg() {
		Code.put(Code.neg);
	}
	
	public static void inc(int x) {
		Code.loadConst(x);
		Code.put(Code.add);
	}
	
	public static void dec(int x) {
		Code.loadConst(x);
		Code.put(Code.sub);
	}
	
	public static void arrayLength() {
		Code.put(Code.arraylength);
	}
	
	public static void newArray(int i) {
		Code.put(Code.newarray);
		Code.put(i);
	}
	
	public static void aload() {
		Code.put(Code.aload);
	}
	
	public static void astore() {
		Code.put(Code.astore);
	}
	
	public static void pop() {
		Code.put(Code.pop);
	}
	
	public static void jmp(int offset) {
		Code.put(Code.jmp);
		//Ovde može doći dodavanje u heš mapu potencijalno
		Code.put2(offset);
	}
	
	public static void jcc(int type, int offset) {
		Code.put(Code.jcc + type);
		//Ovde može doći dodavanje u heš mapu potencijalno
		Code.put2(offset);
	}
	
	public static void read() {
		Code.put(Code.read);
	}
	
	public static void print(int dataSize) {
		Code.loadConst(dataSize);
		Code.put(Code.print);
	}
	
	public static void trap() {
		Code.put(Code.trap);
	}
	
	
}


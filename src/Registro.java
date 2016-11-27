
public class Registro {
	String cp;
	String arco;
	String fn;
	String foglio;
	
	public Registro(String cp,String arco, String fn, String foglio){
		this.cp=cp;
		this.arco=arco;
		this.fn=fn;
		this.foglio=foglio;
	}

	public boolean check_pc(){
		boolean b;
		if(cp.equals("C")||cp.equals("P")){
			b=true;
			System.out.println("sono nel metodo check pc"+cp);
		}
		else
			b=false;
		System.out.println("sono nel metodo checkpc"+cp);

		return b;
	}

	public boolean check_fn(){
		boolean b;
		if(fn.equals("F")||fn.equals("N")){
			b=true;
			System.out.println("sono nel metodo checkfn"+fn);
		}
		else
			b=false;
		System.out.println("sono nel metodo checkfn"+fn);

		return b;		
	}

	public String toString(){
		return cp+"/"+arco+"/"+fn+"/"+foglio;
	}
}
package symboltable;

import java.util.*;
import ast.Definition;

public class SymbolTable {
	
	private int scope=0; //0 global, 1 local…
	private List<Map<String,Definition>> table;

	public SymbolTable()  {
        table = new ArrayList<Map<String, Definition>>();
        table.add(new HashMap<String, Definition>());
	}

	public void set() {
        this.scope++;
        table.add(new HashMap<String, Definition>());
	}
	
	public void reset() {
        this.scope--;
        table.remove(table.size()-1);
	}

    // returns false if the variable was already defined
    public boolean insert(Definition definition) {
        if(findInCurrentScope(definition.getName())==null){
            definition.setScope(scope); //antes de insertarlo, cambiar scope
            table.get(scope).putIfAbsent(definition.getName(), definition);
            return true;
        } else {
            return false;
        }
	}
	
	public Definition find(String id) {
        for(int i=0;i<=scope;i++){
            if(table.get(i).get(id)!=null) {
                return table.get(i).get(id);
            }
        }
        return null;
	}

	public Definition findInCurrentScope(String id) {
        return table.get(scope).get(id);
	}
}

package Project.Symbols;

import Project.inter.Id;

import java.util.HashMap;
import java.util.Map;

public class Env {
    //把字符串词法单元映射为Id的对象
    public Map<String, Id> table ;

    protected Env prev;
    public Env(Env p){
        table = new HashMap<>();
        prev = p;
    }
    public void put(String s,Id sym){
        table.put(s,sym);
    }
    public Id get(String s){
        for (Env e = this;e!=null;e = e.prev){
            Id found = e.table.get(s);
            if(found!=null)
                return found;
        }
        return null;
    }
}

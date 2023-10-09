package model.prgstate.datastructures.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dict<Key, Val> implements IDict<Key, Val> {

    private HashMap<Key, Val> dict;

    public Dict() {
        this.dict = new HashMap<Key, Val>();
    }

    @Override
    public void add(Key key, Val Val) {
        this.dict.put(key, Val);
    }

    @Override
    public void remove(Key key) {
        this.dict.remove(key);
    }

    @Override
    public boolean contains(Key key) {
        return this.dict.containsKey(key);
    }

    @Override
    public Val get(Key key) {
        return this.dict.get(key);
    }

    @Override
    public void update(Key key, Val Val) {
        this.dict.replace(key, Val);
    }

    @Override
    public String toString() {
        if (this.dict.isEmpty())
            return "";
        String result = "";
        for (Key key : this.dict.keySet()) {
            if (key != null)
                result += key.toString() + " -> " + this.dict.get(key).toString() + "; ";
            else
                result += "noVal" + " -> " + this.dict.get(key).toString() + "; ";
        }
        return result;
    }

    @Override
    public Map<Key, Val> getContent() {
        return this.dict;
    }

    @Override
    public List<Val> values() {
        return new ArrayList<Val>(this.dict.values());
    }

    @Override
    public IDict<Key, Val> clone() {
        IDict<Key, Val> clone = new Dict<Key, Val>();
        for (Key key : this.dict.keySet()) {
            clone.add(key, this.dict.get(key));
        }
        return clone;
    }

    @Override
    public void setContent(Map<Key, Val> content) {
        this.dict = new HashMap<Key, Val>(content);
    }
}

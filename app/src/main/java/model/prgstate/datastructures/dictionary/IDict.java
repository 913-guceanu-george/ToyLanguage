package model.prgstate.datastructures.dictionary;

import java.util.List;
import java.util.Map;

public interface IDict<Key, Val> {
    public void add(Key key, Val Val);

    public void remove(Key key);

    public boolean contains(Key key);

    public Val get(Key key);

    public void update(Key key, Val Val);

    public Map<Key, Val> getContent();

    public List<Val> values();

    public IDict<Key, Val> clone();

    public void setContent(Map<Key, Val> content);
}

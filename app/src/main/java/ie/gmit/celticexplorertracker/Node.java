package ie.gmit.celticexplorertracker;

import android.graphics.Bitmap;
import android.text.Editable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elizabeth Wozniak
 */
public class Node implements Serializable {
    private static final long serialVersionUID = 0L;

    private String name;
    private Node parent = null;
    private Bitmap photo;
    private Editable text;
    private List<Node> children = new ArrayList<Node>();

    public Node(String name, Node parent) {
        super();
        this.name = name;
        this.parent = parent;
    }

    public Node(String name, Node parent, Bitmap photo) {
        super();
        this.name = name;
        this.parent = parent;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node(Editable text, Node parent2) {
        super();
        this.text = text;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node child)
    {
        children.add(child);
    }

    public Node getChild(int i)
    {
        if(hasChildren()){
            return this.getChildren().get(i);
        }
        return null;
    }

    public void deleteChild(Node child)
    {
        children.remove(child);
    }

    public void insertChild(Node next,Node existing)
    {
        existing.setParent(next);
        next.setParent(this);
    }

    public List<Node> getChildren() {
        if(hasChildren()){
            return children;
        }
        return null;

    }
    private boolean hasChildren() {

        if(!this.children.isEmpty())
        {
            return true;
        }
        return false;
    }

    public int childrenAmt()
    {
        if(hasChildren()){
            return children.size();
        }
        return 0;
    }

    public void children()
    {
        Node[] temp = new Node[children.size()];
        for (int i = 0; i < children.size(); i++) {
            temp[i]= children.get(i);
        }
    }

    public boolean isLeaf(){

        return children.size()==0;
    }

    public boolean isRoot(){
        return this.parent==null;
    }
}

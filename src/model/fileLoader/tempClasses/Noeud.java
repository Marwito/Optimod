package model.fileLoader.tempClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "noeud")
public class Noeud {
	private int id;
	private int x;
	private int y;

	public Noeud() {
	}

	public Noeud(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	@XmlAttribute(name = "id", required = true)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute(name = "x", required = true)
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@XmlAttribute(name = "y", required = true)
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String toString() {
		return "<noeud id=\"" + this.id + "\" x=\"" + this.x + "\" y=\"" + this.y + "\"/>";
	}
}

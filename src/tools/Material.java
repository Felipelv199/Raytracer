package src.tools;

import java.awt.Color;

/**
 *
 * @author Felipe
 */

public class Material {
	Color color = null;

	/*
	 * Obtains: color: Color Description: Constructor of Material Class
	 */
	public Material(Color color) {
		setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

package model.world;

import java.awt.Point;

public class Cover implements Damageable {
	private int currentHP;
	private int maxhp;

	private Point location;

	public Cover(int x, int y) {
	
		this.currentHP = (int)(( Math.random() * 900) + 100);
		int tmp = this.currentHP;
		location = new Point(x, y);
		this.maxhp =tmp;
	}

	public int getMaxhp() {
		return maxhp;
	}

	public int getCurrentHP() {
		return this.currentHP;
	}

	public void setCurrentHP(int newHp) {
		if (newHp < 0) {
			currentHP = 0;
		
		} else
			currentHP = newHp;
	}

	public Point getLocation() {
		return location;
	}

	

	

}

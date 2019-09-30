package es.unizar.webeng.hello;

import java.net.InetAddress;
import java.util.Objects;

public class RegisterIP {
	
	int numberVisits;
	InetAddress direction;
	
	RegisterIP(){
		
	}

	public int getNumberVisits() {
		return numberVisits;
	}

	public void setNumberVisits(int numberVisits) {
		this.numberVisits = numberVisits;
	}

	public InetAddress getDirection() {
		return direction;
	}

	public void setDirection(InetAddress direction) {
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		return Objects.hash(direction, numberVisits);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		RegisterIP other = (RegisterIP) obj;
		return (Objects.equals(this.direction, other.direction) && !Objects.equals(this.numberVisits, other.numberVisits))
				|| (Objects.equals(this.direction, other.direction) && Objects.equals(this.numberVisits, other.numberVisits));
	}
	
	
}

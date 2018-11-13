package org.dturanski.irealpro.song.web;

import java.util.Arrays;
import java.util.List;

/**
 * @author David Turanski
 **/
public enum Key {
	A_MAJOR, A_FLAT_MAJOR, A_SHARP_MAJOR, A_MINOR, A_FLAT_MINOR, A_SHARP_MINOR,
	B_MAJOR, B_FLAT_MAJOR, B_SHARP_MAJOR, B_MINOR, B_FLAT_MINOR, B_SHARP_MINOR,
	C_MAJOR, C_FLAT_MAJOR, C_SHARP_MAJOR, C_MINOR, C_FLAT_MINOR, C_SHARP_MINOR,
	D_MAJOR, D_FLAT_MAJOR, D_SHARP_MAJOR, D_MINOR, D_FLAT_MINOR, D_SHARP_MINOR,
	E_MAJOR, E_FLAT_MAJOR, E_SHARP_MAJOR, E_MINOR, E_FLAT_MINOR, E_SHARP_MINOR,
	F_MAJOR, F_FLAT_MAJOR, F_SHARP_MAJOR, F_MINOR, F_FLAT_MINOR, F_SHARP_MINOR,
	G_MAJOR, G_FLAT_MAJOR, G_SHARP_MAJOR, G_MINOR, G_FLAT_MINOR, G_SHARP_MINOR;

	public static Key of(String notation) {
		if (notation == null || notation.isEmpty() ) {
			return null;
		}

		List<String> ROOTS = Arrays.asList("A", "B", "C", "D", "E", "F", "G");

		String color = "MAJOR";

		if (notation.toUpperCase().endsWith("MIN") || notation.endsWith("m") || notation.endsWith("-")) {
			color = "MINOR";
		}

		if (notation.length() >= 4) {
			if (notation.length() > 5 || !(notation.toUpperCase().endsWith("MAJ") ||
				notation.toUpperCase().endsWith("MIN"))) {
				throw new IllegalArgumentException("Invalid key notation: " + notation);
			}
		}

		String accidental = "";
		if (notation.length() >= 2) {
			if (notation.substring(1, 2).equals("b")) {
				accidental = "FLAT";
			}
			else if (notation.substring(1, 2).equals("#")) {
				accidental = "SHARP";
			}
		}

		String root = notation.substring(0, 1).toUpperCase();

		if (!ROOTS.contains(root)) {
			throw new IllegalArgumentException("Invalid key notation: " + notation);
		}

		return accidental.isEmpty() ? Key.valueOf(String.join("_", root, color)) :
			Key.valueOf(String.join("_", root, accidental, color));
	}

	public boolean isMajor() {
		return this.name().endsWith("MAJOR");
	}

	public Key relative() {
		switch (this) {
		case A_MAJOR:
			return F_SHARP_MINOR;
		case A_FLAT_MAJOR:
		case G_SHARP_MAJOR:
			return F_MINOR;
		case A_SHARP_MAJOR:
		case B_FLAT_MAJOR:
			return G_MINOR;
		case A_MINOR:
			return C_MAJOR;
		case A_FLAT_MINOR:
		case G_SHARP_MINOR:
			return B_MAJOR;
		case A_SHARP_MINOR:
		case B_FLAT_MINOR:
			return D_FLAT_MAJOR;
		case B_MAJOR:
		case C_FLAT_MAJOR:
			return A_FLAT_MINOR;
		case B_SHARP_MAJOR:
		case C_MAJOR:
			return A_MINOR;
		case B_MINOR:
		case C_FLAT_MINOR:
			return D_MAJOR;

		case B_SHARP_MINOR:
		case C_MINOR:
			return E_FLAT_MAJOR;
		case C_SHARP_MAJOR:
		case D_FLAT_MAJOR:
			return B_FLAT_MINOR;
		case D_MAJOR:
			return B_MINOR;
		case D_SHARP_MAJOR:
		case E_FLAT_MAJOR:
			return C_MINOR;
		case D_MINOR:
			return F_MAJOR;
		case D_FLAT_MINOR:
		case C_SHARP_MINOR:
			return E_MAJOR;
		case D_SHARP_MINOR:
		case E_FLAT_MINOR:
			return G_FLAT_MAJOR;
		case E_MAJOR:
		case F_FLAT_MAJOR:
			return C_SHARP_MINOR;
		case E_MINOR:
		case F_FLAT_MINOR:
			return G_MAJOR;
		case E_SHARP_MAJOR:
		case F_MAJOR:
			return D_MINOR;
		case E_SHARP_MINOR:
		case F_MINOR:
			return A_FLAT_MAJOR;
		case F_SHARP_MAJOR:
		case G_FLAT_MAJOR:
			return E_FLAT_MINOR;
		case F_SHARP_MINOR:
		case G_FLAT_MINOR:
			return A_MAJOR;
		case G_MAJOR:
			return E_MINOR;
		case G_MINOR:
			return B_FLAT_MAJOR;
		}
		return null;
	}
}

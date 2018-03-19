package com.elorri.android.turnover;

import android.content.ContentValues;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Fonction utilitaire pour les contents values
 */
public class YContentValues implements Cloneable {
	private Bundle _values;
	private String _printable_col;

	public YContentValues() {
		_values = new Bundle();
	}

	public YContentValues(Bundle v) {
		_values = v;
	}

	public Bundle getValues() {
		return _values;
	}

	// permet de tester l'existance de la clé
	public Boolean containsKey(String key) {
		return _values.containsKey(key);
	}

	// cette fonction permet de récupérer une valeur de type int en spécifiant une valeur par défaut
	public Integer getAsInteger(String key) {
		return getAsInteger(key, 0);
	}

	public Integer getAsInteger(String key, Integer default_value) {
		if (_values == null) {
			return default_value;
		}
		if (!_values.containsKey(key)) {
			//Tools.addlog("fdvcontentvalues", "cle introuvable : " + key, Tools.StackTraceToString(Thread.currentThread().getStackTrace()));
		}

		Integer tmp = null;
		Object tmpObj = _values.get(key);

		if (tmpObj == null) {
			return default_value;
		}

		if (tmpObj instanceof String) {
			try {
				tmp = (int) Double.parseDouble(tmpObj.toString());
			} catch (Exception e) {
			}
			if (tmp == null) {
				return default_value;
			}
		} else {
			tmp = _values.getInt(key);
		}

		return tmp;
	}

	public Boolean getAsBoolean(String key) {
		return getAsBoolean(key, false);
	}

	public Boolean getAsBoolean(String key, boolean default_value) {
		if (_values == null) {
			return default_value;
		}
		if (!_values.containsKey(key)) {
			//Tools.addlog("fdvcontentvalues", "cle introuvable : " + key, Tools.StackTraceToString(Thread.currentThread().getStackTrace()));
		}
		return _values.getBoolean(key);
	}

	/**
	 * cette fonction permet de récupérer une valeur de type string en spécifiant une valeur par défaut
	 *
	 * @param key
	 * @return
	 */
	public String getAsString(String key) {
		return getAsString(key, "");
	}

	public String getAsString(String key, String default_value) {
		if (_values == null) {
			return default_value;
		}
		if (!_values.containsKey(key)) {
			//Tools.addlog("fdvcontentvalues", "cle introuvable : " + key, Tools.StackTraceToString(Thread.currentThread().getStackTrace()));
		}
		Object tmp = _values.get(key);
		if (tmp == null || tmp.equals("<null>")) {
			return default_value;
		}
		return tmp.toString();
	}

	/**
	 * cette fonction permet de récupérer une valeur de type double en spécifiant une valeur par défaut
	 *
	 * @param key
	 * @return valeur double
	 */
	public Double getAsDouble(String key) {
		return getAsDouble(key, 0.0);
	}

	public Double getAsDouble(String key, Double default_value) {
		if (_values == null) {
			return default_value;
		}
		if (!_values.containsKey(key)) {
			//Tools.addlog("fdvcontentvalues", "cle introuvable : " + key, Tools.StackTraceToString(Thread.currentThread().getStackTrace()));
		}
		Double tmp = null;
		try {
			tmp = Double.parseDouble(_values.get(key).toString());
		} catch (Exception e) {
		}
		if (tmp == null) {
			return default_value;
		}

		return tmp;
	}

	/**
	 * cette fonction permet de récupérer une valeur de type arraylist en spécifiant une valeur par défaut
	 *
	 * @param key
	 * @return valeur double
	 */
	public ArrayList<?> getAsArrayList(String key) {
		return getAsArrayList(key, null);
	}

	public ArrayList<?> getAsArrayList(String key, ArrayList<?> default_value) {
		if (_values == null) {
			return default_value;
		}
		if (!_values.containsKey(key)) {
			//Tools.addlog("fdvcontentvalues", "cle introuvable : " + key, Tools.StackTraceToString(Thread.currentThread().getStackTrace()));
		}
		ArrayList<?> tmp = (ArrayList<?>) _values.getSerializable(key);
		if (tmp == null) {
			return default_value;
		}
		return tmp;
	}

	//surcharge les fonctions classiques
	public void put(String key, String value) {
		if (_values == null) return;
		_values.putString(key, value);
	}

	public void put(String key, Integer value) {
		if (_values == null) return;
		_values.putInt(key, value);
	}

	public void put(String key, Double value) {
		if (_values == null) return;
		_values.putDouble(key, value);
	}

	public void put(String key, Boolean value) {
		if (_values == null) return;
		_values.putBoolean(key, value);
	}

	public void put(String key, Bundle value) {
		if (_values == null) return;
		_values.putBundle(key, value);
	}

	public void put(String key, ArrayList<?> value) {
		if (_values == null) return;
		_values.putSerializable(key, value);
	}


	public void putNull(String key) {
		if (_values == null) return;
		_values.putString(key, null);
	}

	// compte le compte d'élement
	public int size() {
		return _values.size();
	}

	// permet de définir la classe comme printable ( spinner )
	public void setPrintableCol(String col) {
		_printable_col = col;
	}

	public String toString() {
		if (_printable_col != null) {
			return _values.getString(_printable_col);
		} else if (_values.containsKey("printable_name")) {
			return _values.getString("printable_name");
		}
		return "printable_col_undefined";
	}

	// permet de dupliquer le contentvalues
	public YContentValues clone() {
		Bundle new_values = new Bundle();
		new_values.putAll(_values);

		return new YContentValues(new_values);
	}

	/**
	 * Cette méthode permet de récupérer la traduction d'un object si elle existe
	 *
	 * @param cls_id Identifiants de class
	 * @return
	 */
	public YContentValues translate(int cls_id) {
		//Yi18n.getClsTranslation(cls_id, this);
		return this;
	}

	public Bundle toBundle() {
		return _values;
	}

	public ContentValues toContentValues() {
		ContentValues contentValues = new ContentValues();
		for (String key : _values.keySet()) {
			if (_values.get(key) instanceof String) {
				contentValues.put(key, (String) _values.get(key));
			}
			if (_values.get(key) instanceof Byte) {
				contentValues.put(key, (Byte) _values.get(key));
			}
			if (_values.get(key) instanceof Short) {
				contentValues.put(key, (Short) _values.get(key));
			}
			if (_values.get(key) instanceof Integer) {
				contentValues.put(key, (Integer) _values.get(key));
			}
			if (_values.get(key) instanceof Long) {
				contentValues.put(key, (Long) _values.get(key));
			}
			if (_values.get(key) instanceof Float) {
				contentValues.put(key, (Float) _values.get(key));
			}
			if (_values.get(key) instanceof Double) {
				contentValues.put(key, (Double) _values.get(key));
			}
			if (_values.get(key) instanceof Boolean) {
				contentValues.put(key, (Boolean) _values.get(key));
			}
		}
		return contentValues;
	}

	public static String toSql(List<YContentValues> contentValues) {
		String sql = "";
		int i = 0;
		for (YContentValues aContentValue : contentValues) {
			sql += "select * from ";
			sql += aContentValue.toSqlLine() + " union ";
			if (i == contentValues.size() - 1) {
				return sql;
			}
			i++;
		}
		return sql;
	}

	private String toSqlLine() {
		int i = 0;
		String sql = "select ";
		for (String key : _values.keySet()) {
			sql += _values.get(key) + " as " + key;
			if (i != _values.size() - 1) {
				sql += ", ";
			}
			i++;
		}
		return sql;
	}

	public static String toString(List<YContentValues> contentValues) {
		return toString(contentValues, true);
	}

	public static String toString(List<YContentValues> contentValues, boolean withLabel) {
		String string = "";
		int i = 0;
		for (YContentValues aContentValue : contentValues) {
			string += aContentValue.toStringLine(withLabel) + "\n";
			if (i == contentValues.size() - 1) {
				return string;
			}
			i++;
		}
		return string;
	}

	private String toStringLine(boolean withLabel) {
		int i = 0;
		String string = "";
		for (String key : _values.keySet()) {
			if(withLabel){
				string += _values.get(key) + ": ";
			}
			string += _values.get(key) ;
			if (i != _values.size() - 1) {
				string += " | ";
			}
			i++;
		}
		return string;
	}
}

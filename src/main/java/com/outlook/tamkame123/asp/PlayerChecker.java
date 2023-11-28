package com.outlook.tamkame123.asp;

import org.bukkit.Bukkit;

public class PlayerChecker {
	// single
	private static PlayerChecker _instance = new PlayerChecker();
	/**
	 * If the player is 0, increment the counter.
	 * @return count (-1 indicates that this counter is invalid)
	 */
	public static int PlayerCheck() {
		int players = Bukkit.getOnlinePlayers().size();
		// disabled
		if (_instance._isDisabled) {
			return -1;
		}
		// player is 0
		if (players == 0) {
			return ++_instance._counter;
		// player is 1+
		} else {
			// counter reset
			if(_instance._counter != 0) {
				_instance._counter = 0;
			}
			return -1;
		}
	}
	/**
	 * counter disabled.
	 * @param isDisabled <ul><li>true : counter is disabled.</li><li>false: counter is enabled.</li></ul>
	 */
	public static void isDisabled(boolean isDisabled) {
		_instance._isDisabled = isDisabled;
	}

	// counter
	private int _counter = 0;
	// counter is disabled
	private boolean _isDisabled = false;
	// this instance is singleton
	private PlayerChecker() {}
}
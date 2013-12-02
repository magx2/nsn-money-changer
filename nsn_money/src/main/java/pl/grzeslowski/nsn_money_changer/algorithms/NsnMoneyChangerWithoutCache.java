package pl.grzeslowski.nsn_money_changer.algorithms;

import pl.grzeslowski.nsn_money_changer.NsnMoneyChanger;

/**
 * This class does not use anything to cache values.
 * 
 * @author Martin Grześlowski
 * 
 */
public final class NsnMoneyChangerWithoutCache implements NsnMoneyChanger {
	public long waysToChangeMoney(int coinValue) {
		return waysToChangeMoney(coinValue, coinValue / 2);
	}

	private static long waysToChangeMoney(int sum, int largestNumber) {
		if (largestNumber == 1 || sum == 0) {
			return 1;
		} else {

			// group that will use largest number
			long left = waysToChangeMoney(sum - largestNumber, largestNumber);

			// group that don't use largest number
			long right = waysToChangeMoney(sum, largestNumber / 2);

			return left + right;
		}
	}
}

package pl.grzeslowski.nsn_money_changer.algorithms;

import pl.grzeslowski.nsn_money_changer.NsnMoneyChanger;

/**
 * This class uses HashMap to cache previously counted values.
 * 
 * @author Martin Grześlowski
 *
 */
public final class NsnMoneyChangerWithCache implements NsnMoneyChanger {
	private static long[][] cache;

	public long waysToChangeMoney(int coinValue) {
		cache = new long[coinValue][coinValue];

		return waysToChangeMoney(coinValue, coinValue / 2);
	}

	private static long waysToChangeMoney(int sum, int largestNumber) {
		if (largestNumber == 1 || sum == 0) {
			return 1;
		} else {

			// indexing in Java starts from 0 not from 1, so we need to decrease by one our indexes
			int index1 = sum - 1;
			int index2 = largestNumber - 1;

			if (cache[index1][index2] == 0) {

				// group that will use largest number
				long left = waysToChangeMoney(sum - largestNumber,
						largestNumber);

				// group that don't use largest number
				long right = waysToChangeMoney(sum, largestNumber / 2);

				// caching value in table
				cache[index1][index2] = left + right;
			}

			return cache[index1][index2];
		}
	}
}

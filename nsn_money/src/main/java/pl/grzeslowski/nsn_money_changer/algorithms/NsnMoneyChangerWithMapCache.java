package pl.grzeslowski.nsn_money_changer.algorithms;

import java.util.HashMap;
import java.util.Map;

import pl.grzeslowski.nsn_money_changer.NsnMoneyChanger;

/**
 * This class uses simple array of longs (long[][]) to cache previously counted values.
 * 
 * @author Martin Grześlowski
 *
 */
public final class NsnMoneyChangerWithMapCache implements NsnMoneyChanger {
	private static Map<CacheKey, Long> cache;

	public long waysToChangeMoney(int coinValue) {
		cache = new HashMap<NsnMoneyChangerWithMapCache.CacheKey, Long>();

		return waysToChangeMoney(coinValue, coinValue / 2);
	}

	private static long waysToChangeMoney(int sum, int largestNumber) {
		if (largestNumber == 1 || sum == 0) {
			return 1;
		} else {
			CacheKey cacheKey = new CacheKey(sum, largestNumber);
			if (!cache.containsKey(cacheKey)) {

				// group that will use largest number
				long left = waysToChangeMoney(sum - largestNumber,
						largestNumber);

				// group that don't use largest number
				long right = waysToChangeMoney(sum, largestNumber / 2);

				// caching value in table
				cache.put(cacheKey, left + right);

				return left + right;
			}

			return cache.get(cacheKey);
		}
	}

	private static class CacheKey {
		private final long sum;
		private final long largestNumber;

		public CacheKey(long sum, long largestNumber) {
			this.sum = sum;
			this.largestNumber = largestNumber;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ (int) (largestNumber ^ (largestNumber >>> 32));
			result = prime * result + (int) (sum ^ (sum >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CacheKey other = (CacheKey) obj;
			if (largestNumber != other.largestNumber)
				return false;
			if (sum != other.sum)
				return false;
			return true;
		}
	}
}

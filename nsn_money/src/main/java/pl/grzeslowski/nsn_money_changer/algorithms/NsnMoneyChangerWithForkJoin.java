package pl.grzeslowski.nsn_money_changer.algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import pl.grzeslowski.nsn_money_changer.NsnMoneyChanger;

/** 
 * This class uses Fork-Join from java.util.concurrent.<br />
 * Caching is made by HashMap. 
 * 
 * @author Martin Grześlowski
 *
 */
public class NsnMoneyChangerWithForkJoin implements NsnMoneyChanger {
	private static Map<CacheKey, Long> cache;

	public long waysToChangeMoney(int coinValue) {
		cache = Collections.synchronizedMap(new HashMap<CacheKey, Long>());

		final ForkJoinPool pool = new ForkJoinPool();
		Finder finder = new Finder(coinValue, coinValue / 2);
		return pool.invoke(finder);
	}

	private class Finder extends RecursiveTask<Long> {

		/**
		 * Generated serialVersionUID by Eclipse
		 */
		private static final long serialVersionUID = -5872238667425740194L;
		private final long sum;
		private final long largestNumber;

		public Finder(long sum, long largestNumber) {
			super();
			this.sum = sum;
			this.largestNumber = largestNumber;
		}

		@Override
		protected Long compute() {
			if (largestNumber == 1 || sum == 0) {
				return 1L;
			} else {
				CacheKey cacheKey = new CacheKey(sum, largestNumber);
				if (!cache.containsKey(cacheKey)) {
					
					// group that will use largest number
					Finder leftFinder = new Finder(sum - largestNumber,
							largestNumber);
					leftFinder.fork();

					// group that don't use largest number
					Finder rightFinder = new Finder(sum, largestNumber / 2);
					rightFinder.fork();

					Long left = leftFinder.compute();
					Long right = rightFinder.compute();

					// caching value in table
					cache.put(cacheKey, left + right);

					return left + right;
				}

				return cache.get(cacheKey);
			}
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

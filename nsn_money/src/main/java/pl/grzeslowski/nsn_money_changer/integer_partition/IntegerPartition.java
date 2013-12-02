package pl.grzeslowski.nsn_money_changer.integer_partition;

public class IntegerPartition {
	public long findAllPartitions(int n) {
		return findAllPartitions(n, n);
	}

	public long findAllPartitions(int n, int m) {
		if (m <= 0) {
			return 0;
		} else if (n < 0) {
			return 0;
		} else if (n == 0) {
			return 1;
		} else {
			long left = findAllPartitions(n - m, m);
			long right = findAllPartitions(n, m - 1);

			return left + right;
		}
	}
}
package nsn_money;

import java.util.Arrays;
import java.util.Collection;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import pl.grzeslowski.nsn_money_changer.algorithms.NsnMoneyChangerWithCache;

@RunWith(value = Parameterized.class)
public class NsnMoneyChangerWithCacheTest {

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { new CoinResult(4, 3) },
				{ new CoinResult(8, 9) }, { new CoinResult(16, 35) },
				{ new CoinResult(1024, 2320518947l) } };
		return Arrays.asList(data);
	}

	private CoinResult value;

	public NsnMoneyChangerWithCacheTest(CoinResult value) {
		this.value = value;
	}

	@Test
	public void test_diffrent_values_of_coins() throws Exception {

		// given
		NsnMoneyChangerWithCache nsn = new NsnMoneyChangerWithCache();

		// when
		long waysToChangeMoney = nsn.waysToChangeMoney(value.coin);

		// then
		Assertions.assertThat(waysToChangeMoney).isEqualTo(value.result);
	}

	private static class CoinResult {
		private final int coin;
		private final long result;

		public CoinResult(int coin, long result) {
			this.coin = coin;
			this.result = result;
		}
	}
}

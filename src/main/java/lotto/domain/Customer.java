package lotto.domain;

import camp.nextstep.edu.missionutils.Console;
import lotto.config.BaseValidation;

import static lotto.config.BaseValidation.*;

public class Customer {

    private static final String INPUT_MONEY = "구입 금액을 입력해주세요.";
    private static final String BUY_LOTTO_NUMBER = "개를 구매했습니다.";
    private static final int LOTTO_PRICE = 1000;
    private static final int REMAINDER = 0;

    private int money;
    private int hasLotto;

    public void payMoney() {

        System.out.println(INPUT_MONEY);

        try {
            this.money = Integer.parseInt(Console.readLine());
            if ((getMoney() % LOTTO_PRICE) != REMAINDER) {
                throw new IllegalArgumentException(INVALID_PAY_MONEY.getMessage());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }

    }

    public int getMoney() {
        return money;
    }

    public void buyLotto() {
        hasLotto = getMoney() / LOTTO_PRICE;
        System.out.println(hasLotto + BUY_LOTTO_NUMBER);
    }

    public int getHasLotto() {
        return hasLotto;
    }


}

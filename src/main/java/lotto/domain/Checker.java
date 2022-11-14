package lotto.domain;

import camp.nextstep.edu.missionutils.Console;

import java.util.*;
import java.util.Map.Entry;

import static lotto.config.BaseValidation.INVALID_FORMAT;
import static lotto.config.Constant.*;

public class Checker {

    private static final String INSERT_WINNING_NUMBER = "당첨 번호를 입력해 주세요.";
    private static final String INSERT_BONUS_NUMBER = "보너스 번호를 입력해 주세요.";
    private static final String WINNING_STATS = "당첨 통계\n---";
    private static final String THREE_WINNING = "3개 일치 (5,000원) - ";
    private static final String FOUR_WINNING = "4개 일치 (50,000원) - ";
    private static final String FIVE_WINNING = "5개 일치 (1,500,000원) - ";
    private static final String FIVE_WINNING_ONE_BONUS = "5개 일치, 보너스 볼 일치 (30,000,000원) - ";
    private static final String SIX_WINNING = "6개 일치 (2,000,000,000원) - ";
    private static final String SHOW_RETURN_RATE_FRONT = "총 수익률은 ";
    private static final String SHOW_RETURN_RATE_BACK = "%입니다.";

    private List<Integer> winningNumbers = new ArrayList<>();
    private HashMap<Integer, Integer> winningStats = new HashMap<>() {{
        put(3, 0);
        put(4, 0);
        put(5, 0);
        put(6, 0);
        put(7, 0);
    }};
    private int bonusNumber;
    private int key;
    private double returnRate;

    public void insertWinningNumbers() {
        System.out.println(INSERT_WINNING_NUMBER);

        String winningNumber = Console.readLine();
        try {
            int[] numbers = Arrays.stream(winningNumber.split(",")).mapToInt(Integer::parseInt).toArray();

            for (int number : numbers) {
                winningNumbers.add(number);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }

    }

    public void insertBonusNumber() {
        System.out.println(INSERT_BONUS_NUMBER);

        try {
            bonusNumber = Integer.parseInt(Console.readLine());
        } catch (Exception e) {
            throw new IllegalArgumentException(INVALID_FORMAT.getMessage());
        }

    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    public void checkMyLotto(List<Integer> lotto) {

        int oldCount;

        key = 0;

        checkWinningNumber(lotto);

        if (key == 5) { // 당첨 번호가 5개 동일하면 보너스 번호 당첨 여부 확인
            checkBonusNumber(lotto);
        }

        if (winningStats.containsKey(key)) {
            oldCount = winningStats.get(key);
            winningStats.replace(key, oldCount, oldCount + 1);
        }

    }

    private void checkWinningNumber(List<Integer> lotto) {
        for (int number : winningNumbers) {
            if (lotto.contains(number)) {
                key++;
            }
        }
    }

    private void checkBonusNumber(List<Integer> lotto) {
        if (lotto.contains(bonusNumber)) {
            key += 2;
        }
    }

    public HashMap<Integer, Integer> getWinningStats() {
        return winningStats;
    }

    public void showWinningStats() {

        System.out.println(WINNING_STATS);

        for (Entry<Integer, Integer> entry : winningStats.entrySet()) {

            if (entry.getKey() == 3) {
                System.out.println(THREE_WINNING + entry.getValue());
            }

            if (entry.getKey() == 4) {
                System.out.println(FOUR_WINNING + entry.getValue());
            }

            if (entry.getKey() == 5) {
                System.out.println(FIVE_WINNING + entry.getValue());
            }

            if (entry.getKey() == 7) {
                System.out.println(FIVE_WINNING_ONE_BONUS + entry.getValue());
            }

            if (entry.getKey() == 6) {
                System.out.println(SIX_WINNING + entry.getValue());
            }

        }

    }

    public void calculateRateOfReturn(int payMoney) {

        double sum = 0;

        for (Entry<Integer, Integer> entry : winningStats.entrySet()) {

            if (entry.getKey() == 3) {
                sum += THREE_PRICE * entry.getValue();
            }

            if (entry.getKey() == 4) {
                sum += FOUR_PRICE * entry.getValue();
            }

            if (entry.getKey() == 5) {
                sum += FIVE_PRICE * entry.getValue();
            }

            if (entry.getKey() == 7) {
                sum += FIVE_ONE_PRICE * entry.getValue();
            }

            if (entry.getKey() == 6) {
                sum += SIX_PRICE * entry.getValue();
            }

        }

        returnRate = (sum / payMoney) * 100;

    }

    public double getReturnRate() {
        return returnRate;
    }

}

package ru.playtox.config;

import lombok.Getter;
import lombok.Synchronized;
import java.math.BigDecimal;

@Getter
public class AppConfig {
    private static AppConfig instance = null;

    private int numberOfThreads;
    private int numberOfTransactions;
    private int numberOfAccounts;
    private BigDecimal initialFundsBalance;

    private AppConfig() {
        ConfigReader configReader = new ConfigReader();
        numberOfThreads = Integer.parseInt(configReader.getProperty("number.of.threads"));
        numberOfTransactions = Integer.parseInt(configReader.getProperty("number.of.transactions"));
        numberOfAccounts = Integer.parseInt(configReader.getProperty("number.of.accounts"));
        initialFundsBalance = new BigDecimal(configReader.getProperty("initial.funds.balance"));
    }

    @Synchronized
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
}

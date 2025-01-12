package org.example;

import java.sql.*;

public class ComputerLocationRecorder {
    private final Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // Замените на вашу БД
    private static final String USER = "postgres"; // Замените на ваше имя пользователя
    private static final String PASSWORD = "2024"; // Замените на ваш пароль

    public ComputerLocationRecorder() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            createTableIfNotExists();
        } catch (SQLException e) {
            System.out.printf("Ошибка подключения к БД 000%s%n", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Ошибка подключения к БД 000");
        }
    }

    private void createTableIfNotExists() {
        String query = "CREATE TABLE IF NOT EXISTS COMPUTER_LOCATIONS (" +
                "id SERIAL PRIMARY KEY, " +
                "date TEXT, " +
                "time TEXT, " +
                "cabinet_number INTEGER, " +
                "computer_number INTEGER, " +
                "analysis_result TEXT)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public synchronized void recordComputerLocation(int cabinetNumber, int computerNumber, String analysisResult) {
        String query = "INSERT INTO COMPUTER_LOCATIONS (date, time, cabinet_number, computer_number, analysis_result) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("=================================LOG: БД открыта для записи=================================");
            statement.setString(1, LocalDatetimeServer.getDateOnLocalHost());
            statement.setString(2, LocalDatetimeServer.getTimeOnLocalHost());
            statement.setInt(3, cabinetNumber);
            statement.setInt(4, computerNumber);
            statement.setString(5, analysisResult);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка закрытия соединения с БД", e);
        }
    }
}
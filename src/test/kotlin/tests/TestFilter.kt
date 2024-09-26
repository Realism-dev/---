package tests

enum class TestFilter {
    BASE_100_TESTS, // Использовать 100 базовых тестов
    USER_TESTS, // Использовать только тесты пользователя
    ALL_TESTS, // Использовать все тесты
    GENERATE_100, // Сгенерить 100 новых тестов
}
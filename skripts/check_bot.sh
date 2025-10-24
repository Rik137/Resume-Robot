#!/bin/bash
JAR_PATH="/Users/sergejnazarov/IdeaProjects/TelegramBotForHeadHunter/target/TelegramBotForHeadHunter-1.0-SNAPSHOT.jar"
PID_FILE="/tmp/telegram_bot.pid"
LOG_FILE="/tmp/telegram_bot.log"

# Проверяем, есть ли PID-файл
if [ -f "$PID_FILE" ]; then
    PID=$(cat "$PID_FILE")

    # Проверяем, жив ли процесс
    if ps -p "$PID" > /dev/null 2>&1; then
        echo "$(date '+%Y-%m-%d %H:%M:%S') — Бот работает (PID: $PID)" >> "$LOG_FILE"
        exit 0
    else
        echo "$(date '+%Y-%m-%d %H:%M:%S') — PID-файл есть, но процесс не найден. Перезапуск..." >> "$LOG_FILE"
        rm -f "$PID_FILE"
    fi
else
    echo "$(date '+%Y-%m-%d %H:%M:%S') — PID-файл отсутствует. Запускаем бот..." >> "$LOG_FILE"
fi

# Запуск бота
nohup java -jar "$JAR_PATH" >> "$LOG_FILE" 2>&1 &
echo $! > "$PID_FILE"
echo "$(date '+%Y-%m-%d %H:%M:%S') — Бот запущен (PID: $(cat $PID_FILE))" >> "$LOG_FILE"
echo "[$(date '+%Y-%m-%d %H:%M:%S')] Бот успешно запущен (PID: $(cat $PID_FILE))"


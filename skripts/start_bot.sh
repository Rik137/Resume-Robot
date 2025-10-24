#!/bin/bash
JAR_PATH="/Users/sergejnazarov/IdeaProjects/TelegramBotForHeadHunter/target/TelegramBotForHeadHunter-1.0-SNAPSHOT.jar"
PID_FILE="/tmp/telegram_bot.pid"

if [ -f "$PID_FILE" ] && kill -0 $(cat "$PID_FILE") 2>/dev/null; then
    echo "Бот уже запущен"
    exit 1
fi

nohup java -jar "$JAR_PATH" > /tmp/telegram_bot.log 2>&1 &
echo $! > "$PID_FILE"
echo "Бот запущен, PID $(cat $PID_FILE)"

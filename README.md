# Telegram Resume Bot 🚀  
---
## Telegram Resume Bot — интерактивный бот для Telegram, который помогает создавать, редактировать и делиться резюме, навыками и контактами. Поддерживает интеграцию с GitHub и отправку PDF-файлов с резюме.
### ✨ Основные возможности  
1. Команда / Кнопка	Описание  
2. /start	Приветственное сообщение  
3. 📄 Резюме	Заполнение и просмотр резюме  
4. 💡 Навыки	Добавление и отображение навыков  
5. ☎️ Контакты	Сбор контактной информации  
6. 🐱 GitHub	Превью профиля пользователя  
7. 🔍 Проект	Ссылка на портфолио / проект  
8. 📄 Скачать резюме	Отправка PDF с резюме
---
### 🛠 Полный цикл создания бота  
#### 1️⃣ Регистрация через BotFather  
Откройте Telegram и найдите @BotFather.  
Используйте команду /newbot и следуйте инструкциям.  
Настройте имя и username бота.  
Получите токен API для работы бота.  
⚠️ Важно: Никогда не публикуйте токен публично.  

#### 2️⃣ Разработка на Java  
Основной класс запуска: Application.java  
Логика обработки сообщений: ResumeBot.java  
Используется TelegramBots API  

#### 3️⃣ Интеграция с внешними сервисами  
GitHub API для отображения профиля  
Локальные PDF-файлы для отправки резюме  

#### 4️⃣ Скрипты для управления ботом  
Все скрипты находятся в папке scripts/:  
Скрипт	Описание  
start.sh	Запуск бота и сохранение PID  
stop.sh	Остановка бота по PID  
check_bot.sh	Проверка работы бота и перезапуск при падении  
Настройка прав на выполнение:  
```bash
chmod +x scripts/*.sh
```
Автоматизация с cron:  
```bash
*/5 * * * * /path/to/telegram-resume-bot/scripts/check_bot.sh
```
Перезапуск каждые 5 минут при падении бота.  

#### 5️⃣ Запуск бота  

./scripts/start.sh   # Запуск  
./scripts/stop.sh    # Остановка  
./scripts/check_bot.sh  # Проверка и перезапуск  

🔹 Можно использовать VPN, если Telegram недоступен в регионе.
⚙️ Настройка проекта  
Клонируем репозиторий:  
```bash
git clone https://github.com/yourusername/telegram-resume-bot.git
```
cd telegram-resume-bot  
Настраиваем токен и имя бота в ResumeBot.java:  
```java
@Override
public String getBotUsername() {
    return "YourBotName";
}

@Override
public String getBotToken() {
    return "YOUR_BOT_TOKEN";
}
```
Добавляем PDF-файл с резюме в resources/ и указываем путь в методе sendResumePdf.  
Сборка проекта:  
```
mvn clean package
```
```
📂 Структура проекта
src/
 └─ main/
     ├─ java/com/sergey/nazarov/bot/
     │   ├─ Application.java
     │   └─ ResumeBot.java
     └─ resources/
         └─ resume.pdf
scripts/
 ├─ start.sh
 ├─ stop.sh
 └─ check_bot.sh
assets/
 ├─ banner.png
 ├─ bot_ui.png
 └─ demo.gif
```
💻 Технологии  
Java 17+  
TelegramBots API  
Jackson JSON Parser  
Bash скрипты для управления ботом  
GitHub API  
Cron (для автоматизации проверки бота)  
🎬 Пример работы бота  
Интерактивное меню  
Отправка PDF с резюме  
Превью GitHub профиля  

📝 Примечания  
Работает на локальном ПК и VPS  
Важно защитить токен бота и PDF-файлы  
Скрипты позволяют автоматизировать запуск, остановку и мониторинг  
Настройка cron обеспечивает стабильную работу без вмешательства  

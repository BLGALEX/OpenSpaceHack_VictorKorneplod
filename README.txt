Для запуска бота понадобится intellij idea и jdk версии 8 или выше

1) Запускаем intellij idea переходим File> New> Projet from Version Control.
Указываем в поле URL ссылку на репозиторий и жмем clonе.

2) После загрузки всех файлов File> Project Structure> Project Settings> Modules> Sources.
Указываем в поле "Language level" номер версии своего JDK. Жмём Ок.
Далее также  File> Project Structure> Project Settings> Modules> Dependencies.
В поле "Module SDK" указываем путь к своему JDK, версии, указанной ранее. Жмём Ок.


3) Далее File> Project Structure> Project Settings> Project.
Здесь в поле "Project SDK" указываем также путь к своему JDK и 
в поле "Project language level" указываем таке номер версии своего JDK.
Жмём Ок.

4) Далее File> Settings> Build, Execution, Deployement> Compiler> Java Compiler.
В поле "Project bytecode version" указываем номер версии своего JDK и в списке 
"Pre-module bytecode version" в первом элементе (если он есть) указываем в поле "Target bytecode version" указываем номер версии своего JDK.
Жмём Ок.

5) Заходим в File> Project Structure> Project Settings> Modules> Dependencies и жмем "+" справа.
выбираем "JAR or directories.." и в открывшемся меню проводника заходим в папку с названием проекта (по умолчанию "OpenSpaceHack_Victorkorneplod")
OpenSpaceHack_Victorkorneplod> src> main> resources> telegrambots-3.6-jar-with-dependencies.jar; Жмём Ок и ждём 

6) В intellij idea сслева раскрываем папку проекта переходим src> main> java;
Кликаем правой кнопкой по классу "Bot" и выбираем "Run Bot.main()" , ждём и бот запущен!


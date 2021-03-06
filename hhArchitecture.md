Q: Представим, что у нас есть данные, которые мы очень часто читаем
по сравнению с другими(например словарь стран). Как можно это оптимизировать?

A: Зависит от размера списка стран и от информации, с ними связываемой: 
 если это id -> название страны, то можно, наверное, и захардкодить этот маппинг (стран-то меньше 300 даже),
 чтобы он просто всегда был в оперативке (это как в той шутке:
"Q: как быстрее всего найти n-ное простое число среди первой сотни простых чисел?
 A: скопировать из википедии и захардкодить"). Хотя, если нам нужно прямо много языков поддержать в этом словаре,
 то идея с захардкодить уже сомнительная
 (но всё еще валидная, на мой взгляд, если языков десятки, а не сотни).
 Если отойти от того, что данные -- страны и совсем абстрактно посмотреть, то можно перед "долгой" базой
 вроде постгресса прикрутить редис, который использовать как кэш (и чем чаще нам нужны конкретные данные,
 тем меньше шанс, что мы их из этого кэша вытесним (при lru-стратегии, например)).


Q: Что можно сделать, если таблица вакансий стала слишком большой?
Какие есть решения на уровне текущей базы данных? Можно ли ее чем то заменить?

A1а) можно шардировать базу по области вакансии: пусть на разных шардах будут вакансии из максимально
 не связанных друг с другом областей (например, IT, может лежать на одном инстансе с финансами,
 а вот вакансии охранников и разнорабочих будут храниться где-то ещё:
 скорее всего, они не встретятся в одном запросе). Минус этого подхода в том, при запросах,
 в которых не указывается область вакансии, выборка всё равно будет собираться по всем шардам.
A1б) можно шардировать по региону/городу, это всё-таки параметр, который скорее всего будет указан
 пользователем при поиске, и такие выборки по региону будет довольно просто отдавать с одного шарда

A2: Если подумать, как именно может переполнится таблица вакансий, то можно понять,
 что скорее всего она забивается неактивными, архивированными вакансиями, потому что в один момент времени
 относительно немного человек ищут работу (даже если **максимально грубо оценивая** представить,
 что половина населения России (~150млн/2) работоспособна и меняет работу раз в полгода,
 и уровень безработицы +- постоянный, то есть все ищущие работу раз в полгода (~80млн)
 её находят, и, скажем, половина из них (~40мнл) делает это на hh.ru, то есть за полгода закрывается
 ~40 млн вакансий на hh.ru, а среднее время закрытия вакансии ~месяц, в один момент времени
 открытых вакансий не может быть больше 10млн, а это, скорее всего должно помещаться в постгресс).
 Так что в основной базе можно хранить только открытые вакансии, а весь архив выгружать в отдельные базы
 (в которые мало кто и мало зачем будет ходить). Раз в несколько лет их этих архивных баз можно удалять
 вакансии закрывшихся компаний, да и вообще удалять вакансии старше десяти, например,
 лет, если уж совсем поджимает место на диске


Q: Какие вы видите узкие места, возможно неправильно выбранные технологии в текущей схеме
(можно рассмотреть как “нашу” схему, так и схему настоящего hh.ru)
A0: в "нашей" схеме очень много балансеров, возможно, какие-то сервисы стоит объединить в один
 (по крайней мере внешние балансеры можно убрать на первых порах, если говорить о базовой версии
 "на коленке", да и вообще может быть не нужно разделение на api партнеров/api для фронтов/mobile --
 пусть какие-то специфичные обработки данных происходят на клиенте, а апи дергается всеми одно и то же,
 так мы как разработчики не должны поддерживать/тестировать/актуализировать все три версии апи)

A1: в настоящей схеме хх для меня самое ужасающее в текущей схеме -- это 100+ микросервисов. Я вот недавно
 сменила работу и в ответственности нашей команды что-то около 6 сервисов (может, не слишком уж и "микро"),
 и разбираться в их взаимодействиях -- боль; кажется, что когда есть около сотни сервисов,
 да ещё и на разных языках, даже при том, что тут все запросы к этим микросервисам
 (и тем трём отдельно выделенным, как я понимаю, тоже уже не "микро"), судя по схеме,
 идут через logic/jlogic, должна быть очень подробная документация и интро для новичков,
 иначе со временем может появиться дуплицируемая логика в каких-то сервисах, просто потому что
 кто-то не знал, что логика, которую он собирается написать, реализована уже в каком-то другом сервисе.
 Это вопрос межкомандного взаимодействия больше чем вопрос архитектуры, но тем не менее.

A2: вероятно, можно еще похоливарить про технологии, например, можно предложить разработку каких-то новых
 сервисов можно вести на котлине, потому что его любят, писать на нем приятнее, чем на джаве (это
 субъективщина, конечно, но в каком-то исследовании он фигурировал как the most loved programming languages)
 Когда котлинмультиплатформ (https://kotlinlang.org/docs/multiplatform.html) будет стабильным,
 это позволит переиспользовать код между мобильными/браузерными клиентами
 (и казалось бы, причем тут архитектура бэкенда? Но если фронты будут использовать тот же язык,
 что используется на бэке, они смогут сами решать какие-то незначительные проблемы и вообще разработчики
 в команде будут более fullstack)
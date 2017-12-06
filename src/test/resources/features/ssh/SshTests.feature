# language: ru
Функционал: Тесты по SSH

  Предыстория:
    Пусть установлено соединение по SSH с alpha

  @romano
  Сценарий: Копирование, просмотр и удаление тестового файла
    Если удаленно создать в домашней директории тестовый каталог auto_tests_check
    И в этот каталог загрузить файл TestFileForAutotests.txt
    То файл TestFileForAutotests.txt успешно загружается в тестовую директорию
    И файл загружен с корректным содержимым
    """
      Lorem ipsum dolor sit amet, consectetur adipiscing elit,
      sed do eiusmod tempor incididunt ut labore et dolore magna
      aliqua. Ut enim ad minim veniam, quis nostrud exercitation
      ullamco laboris nisi ut aliquip ex ea commodo consequat.
      Duis aute irure dolor in reprehenderit in voluptate velit
      esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
      occaecat cupidatat non proident, sunt in culpa qui officia
      deserunt mollit anim id est laborum.
    """
    Если удалить с сервера тестовый файл TestFileForAutotests.txt вместе с тестовой директорией
    То тестовые данные успешно удалены с сервера

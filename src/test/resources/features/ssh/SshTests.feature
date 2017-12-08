# language: ru
Функционал: Тесты по SSH

  Предыстория:
    Дано установлено соединение с alpha
    И установлено соединение с beta

  @romano
  Сценарий: Копирование, просмотр и удаление тестового файла
    Если удаленно создать в домашней директории alpha тестовый каталог auto_tests_check
    И в этот каталог загрузить файл TestFileForAutotests.txt
    То файл TestFileForAutotests.txt успешно загружается в тестовую директорию alpha
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
    Если сохранить загруженный alpha тестовый файл
    И загрузить этот файл beta в тестовую директорию
    То файл успешно копируется beta
    Если удалить у alpha и beta тестовый файл TestFileForAutotests.txt вместе с тестовой директорией
    То тестовые данные успешно удалены у alpha
    И тестовые данные успешно удалены у beta

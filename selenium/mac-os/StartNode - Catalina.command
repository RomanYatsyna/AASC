#!/bin/zsh

# shellcheck disable=SC2164
cd "$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

java -Dwebdriver.chrome.driver=chromedriver -jar selenium-server-standalone-3.141.59.jar -role node -hub http://localhost:4444/grid/register/
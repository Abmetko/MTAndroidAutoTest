setting up runner in idea:
Test kind: Suite
Suite: C:/Users/abmet/Desktop/AppiumCucumberTestProject/src/test/resources/testng.xml
VM options: -ea -Dtestng.dtd.http=true

run with maven:
mvn test -Dapp_url=bs://873b5d270e660379f678a85b13080a92a9984ff1 -Dapp_args=com.mobiletrade.fms.globaltradeatf,GlobalTradeATF
mvn test -Dapp_url=bs://2a80ae3286b149211adf58d37d9f6669d0f368b1 -Dapp_args=com.roinvesting,ROInvesting
mvn test -Dapp_url=bs://9c3fd91b89f3a69ca4ae8a89f40018be830ac895 -Dapp_args=com.101investing,101Investing
mvn test -Dapp_url=bs://1b9dec2b2dd905bc108c270ab2a98de290a20b46 -Dapp_args=com.investlite,InvestLite.com
mvn test -Dapp_url=bs://7116d5f2d963138854dc39c695e5cafb368835f3 -Dapp_args=com.primefine,PrimeFin
mvn test -Dapp_url=bs://6de8d3d75a562c8ae6d3aab84410c660220f2984 -Dapp_args=com.etfinance,ETFinance

npm install -g appium@1.18.3
appium -v
npm uninstall appium
appium
//by default 127.0.0.1 4723
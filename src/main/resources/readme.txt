setting up runner in idea:
Test kind: Suite
Suite: C:/Users/abmet/Desktop/AppiumCucumberTestProject/src/test/resources/testng.xml
VM options: -ea -Dtestng.dtd.http=true

run with maven:
mvn test -Dapp_url=bs://873b5d270e660379f678a85b13080a92a9984ff1 -Dapp_args=com.mobiletrade.fms.globaltradeatf,GlobalTradeATF
mvn test -Dapp_url=bs://9d3d7fd538a42079c2a822bb16271a211804eedc -Dapp_args=com.roinvesting,ROInvesting
mvn test -Dapp_url=bs://2775b22dbe80bfeddeac95f439fc327471b6d5b4 -Dapp_args=com.101investing,101Investing
mvn test -Dapp_url=bs://1b9dec2b2dd905bc108c270ab2a98de290a20b46 -Dapp_args=com.investlite,InvestLite.com
mvn test -Dapp_url=bs://7116d5f2d963138854dc39c695e5cafb368835f3 -Dapp_args=com.primefine,PrimeFin
mvn test -Dapp_url=bs://2e61c22def6f361b263ea9fedc5327f576df889f -Dapp_args=com.etfinance,ETFinance

mvn test -Dapp_url=bs://64d1113404f2429a0127ddaf6b52d43efd0ede7b -Dapp_args=au.com.hftrading,HFTrading

npm install -g appium@1.18.3
appium -v
npm uninstall appium
appium
//by default 127.0.0.1 4723

Command + Shift + Dot

mvn clean package -Dmaven.test.skip=true

java --enable-preview -jar TestRunAppiumServer-1.0-SNAPSHOT.jar

runner config:
{
TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();
        suites.add("src/test/resources/testng.xml");
        testng.setTestSuites(suites);
        testng.run();
}


reflection:
try {
            Field field = DriverFactory.class.getDeclaredField("driver");
            field.setAccessible(true);
            try {
                field.set(driver,null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
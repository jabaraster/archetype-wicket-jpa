wicket-jpaプロジェクトの動作のさせ方

# 事前準備

## JDK6
JDK6以上を予めインストールしておいて下さい.  

## Maven
このファイルが見られているということは、既にMavenがインストールされていることでしょう！

## JDK6
6以上が必須です.  

# eclipse対応にするには
次のコマンドで、eclipseプロジェクトが作成されます.  

```
mvn eclipse:eclipse
```

後は、eclipseにインポートして下さい.  

# Webアプリ起動方法

## コマンドラインで起動する
次のコマンドで、Webアプリが起動します.  

```
mvn compile exec:java
```

テストをスキップしたい場合は次のコマンド.  

```
mvn -DskipTests=true compile exec:java
```

## eclipse上で起動する
```${package}.WebStarter```クラスを実行して下さい.  

コンソールに次の表示が出たら起動しています.  

```
********************************************************************
*** WARNING: Wicket is running in DEVELOPMENT mode.              ***
***                               ^^^^^^^^^^^                    ***
*** Do NOT deploy to your live server(s) without changing this.  ***
*** See Application#getConfigurationType() for more information. ***
********************************************************************
```

## Webアプリへのアクセス

次のURLにアクセスすると、Webアプリが使えます.  

<http://localhost:8081>

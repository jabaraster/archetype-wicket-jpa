JavaでWebアプリケーションを作成するためのプロジェクトの雛形を提供します.

# 使い方
## 事前準備
### JDK7(以上)の導入
ご自分の環境に合った方法でJDKをインストールしておいて下さい.  
バージョンは7以上にして下さい.  

### Mavenの導入
Mavenをセットアップしておいて下さい.  
バージョンは3系にして下さい.  

下記のサイトからZIPをダウンロードし、ZIPを解凍した中身を適当な場所に置き、PATHを通しておいて下さい.  
<http://maven.apache.org/>

### インターネット接続
JARファイル群をダウンロードするためにインターネット接続が必須です.  

### Less対応
当プロジェクトでは、CSSを楽に書くための ``` Less ``` という言語を利用しています.  
``` .less ```ファイルは、コンパイルすると ``` .css ``` ファイルになります.  
 ``` Less ``` の利用は必須ではありませんが、是非使うようにして下さい.  
 または別のCSSメタ言語（[Sass](http://sass-lang.com)など）を使っても良いでしょう.  

 ``` Less ``` を使うなら、以下のリンクからコンパイル用アプリをインストールしておいて下さい.  

#### Mac
[Less.app](http://incident57.com/less/)

#### Windows
[WinLess](http://winless.org)

## まずはQuickStart
次の３つのコマンドを実行すると、Webアプリが動きます！  

```
$ mvn -B archetype:generate -DgroupId=sandbox -DartifactId=QuickStart -Dpackage=sandbox.quickstart -DarchetypeCatalog=http://jabaraster.github.io/maven/archetype-catalog.xml -DarchetypeGroupId=jabaraster -DarchetypeArtifactId=archetype-wicket-jpa
$ cd QuickStart
$ mvn compile exec:java
```

コンソールに次のメッセージが表示されたら起動完了です.  

```
********************************************************************
*** WARNING: Wicket is running in DEVELOPMENT mode.              ***
***                               ^^^^^^^^^^^                    ***
*** Do NOT deploy to your live server(s) without changing this.  ***
*** See Application#getConfigurationType() for more information. ***
********************************************************************
4 24, 2013 1:09:40 午後 com.sun.jersey.server.impl.application.WebApplicationImpl _initiate
情報: Initiating Jersey application, version 'Jersey: 1.8 06/24/2011 12:17 PM'
4 24, 2013 1:09:41 午後 com.sun.jersey.server.impl.application.DeferredResourceConfig$ApplicationHolder <init>
情報: Instantiated the Application class sandbox.quickstart.web.rest.RestApplication
4 24, 2013 1:09:56 午後 com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory getComponentProvider
情報: Binding sandbox.quickstart.web.rest.EmployeeResource to GuiceInstantiatedComponentProvider
```

次のURLにアクセスしてみて下さい.  
<http://localhost:8081>


## 詳説・プロジェクト作成方法
次のコマンドが、雛形から新しいプロジェクトを作るコマンドです.  

```
$ mvn -B archetype:generate -DgroupId=<グループ名> -DartifactId=<プロジェクト名> -Dpackage=<パッケージ名> -DarchetypeCatalog=http://jabaraster.github.io/maven/archetype-catalog.xml -DarchetypeGroupId=jabaraster -DarchetypeArtifactId=archetype-wicket-jpa
```

コマンドの引数の意味は次の通りです.  

|引数  |意味|
|:-------------:|:----------------|
| -DgroupId=<グループ名> | これから作るプロジェクトのグループ名 |
| -DartifactId=<プロジェクト名> | これから作るプロジェクトの名前 |
| -Dpackage=<パッケージルート> | これから作るプロジェクトのJavaクラスのパッケージルート |
| -DarchetypeCatalog=http://jabaraster.github.io/maven/archetype-catalog.xml | プロジェクト雛形の所在が書かれたファイル  _※変更の必要なし_ |
| -DarchetypeGroupId=jabaraster | プロジェクト雛形のグループ名  _※変更の必要なし_ |
| -DarchetypeArtifactId=archetype-wicket-jpa | プロジェクト雛形のアーティファクト名  _※変更の必要なし_ | 


さらに、作ったプロジェクトをeclipseでインポート可能にするには、次のコマンドを実行します.  
```
$ cd <プロジェクト名>
$ mvn eclipse:eclipse
```


# 雛形が提供する機能


雛形を基に作成されたプロジェクトには、既に次のような機能が実装されています。


## Webアプリの起動
次のクラスを起動することでWebアプリを起動出来ます.  

```
<パッケージルート>.WebStarter
```

またmvnでも起動可能です.  

```
$ mvn compile exec:java
```

起動したWebアプリには次のURLでアクセス可能です.  
<http://localhost:8081/>

## UIフレームワーク
UIを担うフレームワークとして _Wicket_ が使用可能な状態になっています.  
<http://wicket.apache.org>  

### UIのURL
UIのURLは```/ui/```以下に割り当てています.

例えばトップページは次のURLでアクセスします.  

<http://localhost:8081/ui/>

### 共通レイアウト

### ログイン画面
ログインしていない状態で次のURLにアクセスすると、ログイン画面が開きます.  
<http://localhost:8081/ui/>

管理者ユーザが初期登録されており、次のユーザ名/パスワードでログイン可能です.  

```
admin / admin
```



### トップ画面  
ログイン後に遷移する画面です.  
トップ画面にはAjax処理のサンプルとログアウトリンクがあります.  

### ログアウト
ログアウト処理を行いログイン画面にリダイレクトします.  

### デフォルトスタイルシート  
Twitterの[Bootstrap](http://getbootstrap.com) (Version 3.0.0)を組み込んでいます.  

### DBアクセス  
JPAによるDBアクセスが可能な状態になっています.  
データベースファイル群は、target/db/の下に作成されます.  

DBには組み込み利用可能な _H2Database_ を採用しています.  
<http://www.h2database.com/>



### __H2Databaseの注意点__
事前準備を極力少なく済ませるためにH2Databaseを使っていますが、このDBは __業務システムの本番環境に使うには不安がある__ 点にご注意下さい.  
別途、本番用のDB製品を選定し、その製品を用いた環境を準備してテストする必要があります.    

また開発環境と本番環境のDB製品が異なる場合、 __生SQLを使えない__ ことに注意が必要です.  
DBアクセスは極力JPAの _Qriteria Query_ か _JPQL_ を使って下さい.  

どうしても生SQLを使わなければならない場合は、開発環境を本番環境に合わせるべきです.  
この場合、<パッケージルート>.WebStarterクラスを修正する必要があります.  



## DI設定
DIアーキテクチャによるオブジェクト依存性注入が可能な状態になっています.  
DIコンテナにはWicketと相性が良いGuiceを採用しています.  
[本家サイト](http://code.google.com/p/google-guice/)  
[Wikipediaによる解説](http://ja.wikipedia.org/wiki/Google_Guice)

## RESTインターフェイス
JAX-RSによるRESTインターフェイスが提供可能な状態になっています.  

RESTのURLは```/rest/```以下に割り当てています.  
サンプルとして提供している、全従業員情報情報にREST形式にアクセスするには、次のURLを使います.  

<http://localhost:8081/rest/employee/all>

JAX-RSの実装には _Jersey_ を採用しています.  
<https://jersey.java.net>



# 読んでおきたいページ(整備中)
## Wicketのベストプラクティス
http://javelindev.jp/blog/tech/entry/apache_wicket_ベストプラクティス_日本語


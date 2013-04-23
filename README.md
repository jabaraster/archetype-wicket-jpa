Wicet + JPA (+ JERSEY) での開発環境の雛形です.

# 提供形態
Mavenのarchetype(プロジェクトの雛形)です.  

# 事前準備

## JDK6(以上)の導入
ご自分の環境に合った方法でJDKをインストールしておいて下さい.  
バージョンは6以上にして下さい.  

## Mavenの導入
Mavenをセットアップしておいて下さい.  
バージョンは3系にして下さい.  

下記のサイトからZIPをダウンロードし、ZIPを解凍した中身を適当な場所に置き、PATHを通しておいて下さい.  
<http://maven.apache.org/>

# 利用方法
次のコマンドを打つと、Wicket + JPA + JERSEY(JAX-RS実装)がセットアップされたプロジェクトが作成されます.  

```
mvn -B archetype:generate -DgroupId=sandbox -DartifactId=Aad -Dpackage=sandbox.aad -DarchetypeCatalog=http://jabaraster.github.io/maven/archetype-catalog.xml -DarchetypeGroupId=jabaraster -DarchetypeArtifactId=archetype-wicket-jpa
```

#### コマンドの引数の意味
|引数  |意味|
|:-------------:|:----------------|
| -DgroupId=<グループ名> | これから作るプロジェクトのグループ名 |
| -DartifactId=<アーティファクト名> | これから作るプロジェクトのアーティファクト名 |
| -Dpackage=<パッケージ名> | これから作るプロジェクトのJavaクラスのパッケージルート |
| -DarchetypeCatalog=http://jabaraster.github.io/maven/archetype-catalog.xml | プロジェクト雛形の所在が書かれたファイル  _※変更の必要なし_ |
| -DarchetypeGroupId=jabaraster | プロジェクト雛形のグループ名  _※変更の必要なし_ |
| -DarchetypeArtifactId=archetype-wicket-jpa | プロジェクト雛形のアーティファクト名  _※変更の必要なし_ | 

# 作成されるプロジェクトの環境
#### この章で用いる表記について

| 表記          | 意味      |
|:-------------:|:----------|
| ${artifactId} | プロジェクトを作成したときのmvnコマンドで指定したartifactId. |
| ${package}    | プロジェクトを作成したときのmvnコマンドで指定したpackage. Javaのパッケージのルートになります. |

## Webアプリの起動
次のクラスを起動することでWebアプリを起動出来ます.  

```
${package}.WebStarter
```

またmvnでも起動可能です.  

```
mvn compile exec:java
```

起動したWebアプリには次のURLでアクセス可能です.  
<http://localhost:8081/>

## DB
H2Databaseを使用しています.  
<http://www.h2database.com/>

組み込みモードで動作します.  
データベースファイル群は、target/db/の下に作成されます.  

### __注意点__
事前準備を極力少なく済ませるためにH2Databaseを使っていますが、このDBは__業務システムの本番環境に使うには不安がある__点にご注意下さい.  
別途、本番用のDB製品を選定し、その製品を用いた環境を準備してテストする必要があります.    

また開発環境と本番環境のDB製品が異なる場合、__生SQLを使えない__ことに注意が必要です.  
DBアクセスは極力JPAの_Qriteria Query_か_JPQL_を使って下さい.  

どうしても生SQLを使わなければならない場合は、開発環境を本番環境に合わせるべきです.  
この場合、${package}.WebStarterクラスを修正する必要があります.  

## JPA
DataSourceを使って動作します.  
DataSourceは、次の名前でJNDIに登録されている必要があります.  

```
jdbc/${artifactId}
```

### JNDIへのDataSourceの登録について
#### ${package}.WebStarterクラスを使ってWebアプリを起動する場合
このクラスの中でJNDI登録が行われます.  

#### JavaEEコンテナにデプロイする場合
この場合、JavaEEコンテナでJNDI登録を行なって下さい.  

#### Herokuにデプロイする場合
（検証中）

## Web環境

### UI
UIを担うフレームワークとして_Wicket_を採用しています.  
<http://wicket.apache.org>  

バージョンは```6.4.0```です.  

#### UIのURL
UIのURLは```/ui/```以下に割り当てています.

例えばトップページは次のURLでアクセスします.  

<http://localhost:8081/ui/>

### REST
HTTP通信をプログラムから行うケースに備え、REST形式のインターフェイスを提供する_JAX-RS_を採用しています.  

JAX-RSの紹介記事はこちら.  
<http://www.opentone.co.jp/news/release/article04/article0401.html>

ただ、JAX-RSは仕様（≒規格）の名前です.  
実装として _Jersey_を採用しています.  
<https://jersey.java.net>

#### RESTのURL
RESTのURLは```/rest/```以下に割り当てています.  
サンプルとして提供している、全従業員情報情報にREST形式にアクセスするには、次のURLを使います.  

<http://localhost:8081/rest/employee/all>

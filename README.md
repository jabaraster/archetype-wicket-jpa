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

# 作成されるプロジェクトの環境
## この章で用いる表記について

| キー          | 意味      |
|:-------------:|:----------|
| ${artifactId} | プロジェクトを作成したときのmvnコマンドで指定したartifactId. |
| ${package}    | プロジェクトを作成したときのmvnコマンドで指定したpackage. Javaのパッケージのルートになります. |

## Webアプリの起動
開発環境では、次のクラスを起動することでWebアプリを起動出来ます.  

```
${package}.WebStarter
```

またmvnでも起動可能です.  

```
mvn compile exec:java
```


## DB
H2Databaseを使用します.  
<http://www.h2database.com/>

組み込みモードで動作します.  
データベースファイル群は、target/db/の下に作成されます.  

### __注意点__
事前準備を極力少なく済ませるためにH2Databaseを使っていますが、このDBは__業務システムの本番系で使うには不安がある__点にご注意下さい.  
業務システム開発での運用では、別途本番と同じDB製品を用いた環境を準備し、そこでテストする必要があります.  

また開発環境と本番環境のDB製品が異なる場合、__生SQLを使えない__ことに注意が必要です.  
DBアクセスは極力JPAを使って下さい.  

どうしても生SQLを使わなければならない場合は、開発環境に本番環境のDB製品を導入し、${package}.WebStarterクラスを書き直して下さい.  

## JPA
DataSourceを使って動作します.  
DataSourceは、次の名前でJNDIに登録されている必要があります.  

```
jdbc/${artifactId}
```

${artifactId}には、プロジェクトを作成するときに打ったmvnコマンドで指定したartifactIdが入ります.  

WebStarterクラスを使ってWebアプリを起動する場合、このクラスの中でJNDI登録が行われます.  


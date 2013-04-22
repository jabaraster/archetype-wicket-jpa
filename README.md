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
## 前提

| キー          | 意味      |
|:-------------:|:----------|
| ${artifactId} | プロジェクトを作成したときのmvnコマンドで指定したartifactId. |
| ${package}    | プロジェクトを作成したときのmvnコマンドで指定したpackage. Javaのパッケージのルートになります. |

## Webアプリの起動


## DB
H2Databaseを使用します.  
<http://www.h2database.com/>

組み込みモードで動作します.  
データベースファイル群は、target/db/の下に作成されます.  

## JPA
DataSourceを使って動作します.  
DataSourceは、次の名前でJNDIに登録されている必要があります.  

```
jdbc/${artifactId}
```

${artifactId}には、プロジェクトを作成するときに打ったmvnコマンドで指定したartifactIdが入ります.  

WebStarterクラスを

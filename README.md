Naviagationの注意、androidxのコンポーネントを使わないとうまくいかない:https://developer.android.com/guide/navigation/navigation-getting-started?hl=ja
	
	[MainActivity]
		・MainActivityは数あるアクティビティのうちの一つです。アクティビティはAndroidアプリのUI(ボタンやインプットボックスなど)を表示したり、ユーザーからのインプットを受け取ったりするアプリの
		核をなすファイルです。ア
		プリが起動したとき、アプリはAndroidManifest.xmlファイルに記されたアクティビティを呼び出します。
		アクティビティとレイアウトはレイアウトインフレーションと呼ばれるプロセスによって接続されます。アクティビティが起動したときに、XMLレイアウトファイルに定義されたビューがメモリー上でKotlin
		ビューオブジェクトと呼ばれるデータに変わります。このレイアウトインフレーションによって、アクティビティはそれらのオブジェクトを画面上に描写したり、動的に変更したりできるようになります。
		
		・AppCompatActivityはActivityというクラスのサブクラスです。Activityクラスは以前のAndroidバージョンに対する下位互換性をサポートしています。開発するアプリをできるだけ多くの
		　	ユーザーのデバイスで利用できるようにするために、常にAppCompatActivityクラスを利用します。
		
		・onCreate()メソッドの中で、どのレイアウトがアクティビティと紐づくものなのかを指定します。そしてレイアウトをインフレートします。
			setContentView()メソッドがそれらを両方ともこなしてくれています。ちなみに、Rクラス(画像、string、レイアウトファイルの中の要素などが参照できる）はアプリをビルドした際に作
			られます。Rクラスにはresディレクトリーのコンテンツを含めたすべてのアセットが含まれています。
	[Naviagation]
		・ナビゲーションを使うためには、Gradleファイルにナビゲーションに関する依存関係(dependencies)という宣言文を追加する必要がある。(バージョンについては調べる必要が有)
		
		implementation("android.arch.navigation:navigation-fragment-ktx:$navaigationVersion")
		implementation("android.arch.navigation:navigation-ui-ktx:$navaigationVersion")
		
		・ナビゲーショングラフを追加する
			1,プロジェクトパネルのresフォルダーの上で右クリックして、New > Android Resource Fileを選択してください。
			2,New Resource FileダイアログでResource typeにNavigationを選択してください。
			3,File nameフィールドにはnavigationと入力してください。
			4,Chosen qualifiersボックスが空白であることを確認し、OKをクリックしてください。res > navigationフォルダーにnavigation.xmlというファイルが作成されます。
		
		・NavHostFragmentを作成する
			・ナビゲーションホストフラグメント（Navigation host fragment)はナビゲーショングラフ内のフラグメントのホストとしての役割を果たします。ナビゲーションホストフラグメントは通常、
				NavHostFragmentと名付けられます。ユーザーがナビゲーショングラフに定義された行先間で移動するとき、ナビゲーションホストフラグメントが必要に応じてフラグメントを入
				れ替えます。また、ナビゲーションホストフラグメントには適切なフラグメントのバックスタックを作成し、管理する役割もあります。
			
			手順：
				1,res > layout > activity_main.xmlを開いて、Textタブにしてください。
				2,activity_main.xmlファイル内のTitleFragmentの名前をandroidx.navigation.fragment.navHostFragmentに変更してください。
				3,IDをmyNavHostFragmentに変更してください。
				4,ナビゲーションホストフラグメントはどのナビゲーショングラフリソースを使うのか知っている必要があります。そのために、app:navGraph属性を追加し、ナビゲーショングラフ
				リソースである@navigation/navigationを値にセットしてください。
				5,app:defaultNavHost属性を追加し、値を”true”にしてください。これでナビゲーションホストがでデフォルトホストとなり、システムの戻るボタンを遮断します。
				
		・ナビゲーショングラフを編集する
			1,最初に表示するフラグメントを追加する。また、プレビューに”Preview Unavailable”というメッセージが表示されている場合、Textタブをクリックしてfragment要素が
				tools:layout=”@layout/~”を含んでいるか確認してください。
			2,遷移する各フラグメントを追加し、接続点をつなぎ、アクションを生成する。
			3,Fragment.ktでアクションを利用し遷移する処理を記述する。以下は例となる。
				binding.playButton.setOnClickListener { view : View ->
					view?.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
					}
			
	　　・条件付きナビゲーションを追加する
			条件の違いによって遷移先のフラグメントを変更することも可能。
			ナビゲーショングラフにて、フラグメントの分岐元から、条件によって分岐する複数のフラグメントのアクションを生成する。フラグメントにif文等を用いて条件を追加する。以下は例。
				if(isFlag){
					view.findNavController().navigate(R.id.action_gameFragment_to_gameWonFragment)
					} else{
					view.findNavController().navigate(R.id.action_gameFragment_to_gameOverFragment)
					}
	   
	   ・戻るボタンからのスタック変更
			Androidシステムはユーザーが遷移した画面の履歴を端末上に保存しています。ユーザーが端末上で新しい遷移先に移動する度、Androidシステムは行先をバックスタックに追加します。
			　デフォルトではバックスタックのトップはユーザーが最後に表示した画面になるように設定されています。ナビゲーションアクションはバックスタックを修正することができます。
			
			・アクションに"pop behavior"を設定する
				・アクションのpopUpTo属性は遷移前にバックスタックに含まれている遷移先が削除されます。
				・popUpToInclusive属性がfalse、または設定されていない場合、popUpToは指定した遷移先までの遷移先を削除し、指定した遷移先のみがバックスタックに残ります。
				・popUpToInclusiveがTrueの場合、popUpToは指定した遷移先を含む全ての遷移先をバックスタックから削除します。
				・popUpToInclusiveがTrueでpopUpToがアプリの最初の画面に設定されている場合、アクションは全ての遷移先をバックスタックから削除します。戻るボタンはアプリ外に
					遷移させるようになります。
			
			
				
	[Fragment]
	
	  ⚠API28まではandroid.app.Fragmentが使われていたが非推奨となり、現在はJetpackのFragmentライブラリが推奨されているが、概要を知りたい場合は、android.app.Fragmentを参照する
		
		・フラグメントを作成した際、Activityは<FragmentLayout>で作成されているが、変更しても問題ない。
		
		・フラグメントはアクティビティ内のユーザーインターフェース(UI)の動きや部位を示すものです。一つのアクティビティで複数のフラグメントを組み合わせることによって、マルチペインのUIを作ったり、
			複数のアクティビティでフラ
		グメントを使いまわすことができます。フラグメント＝アクティビティの基本単位。
		
		・フラグメントは独自のライフサイクルを持っており、独自の入力イベントを受け取ります。
		
		・アクティビティ実行中にフラグメントを追加・削除できます。
		・フラグメントはKotlinクラスに定義されています。
		・フラグメントのUIはXMLレイアウトファイルに定義されています。
		
		・onCreateView(LayoutInflater, ViewGroup, Bundle)→フラグメントにユーザーインターフェイスビュー(Activity?)をインスタンス化させるために呼び出されます。
		
		・フラグメントをコンパイルするためにはバインディングオブジェクトを作成し、フラグメントのビューをインフレート（アクティビティにおけるsetContentView()と同等）する必要があります。
			フラグメントのビューをインフレートするために、DataBindingUtil.inflate()関数をフラグメントのバインディングオブジェクト(~Binding)で呼び出してください。
			DataBindingUtil.inflate()には以下の4つの引数を渡す。
				
				・inflater: ここでLayoutInflaterは結合するレイアウトをインフレートするために利用されています。
				・インフレートするレイアウトのXMLレイアウトリソース。レイアウトの一つで、既に定義されているR.layout.fragment_titleを利用します。
				・親ビューグループのコンテナ。(この引数は任意です）
				・attachToParentという値用のfalse
		
	[アプリバー]	
		・アプリバーはアクションバーとも呼ばれ、アプリのブランディングと識別に有用なスペースです。アプリバーの色を設定することもできます。アプリバーはオプションメニューのような馴染みのあるナビゲー
			ションツールをユーザーに提供します。アプリバーからオプションメニューにアクセスするには、縦に並んだ三つの点のアイコンをタップします。アプリバーには画面ごとに変更できるタイトル文字
			を表示させることができます。
		
		・アップボタン：Androidアプリは画面上、アプリバーの左上部にアップボタンを表示することもできます。タイトル画面等では、アプリの画面ヒエラルキーにおいてトップにあるので、ユーザ０がタイトル画
			面にいる場合は、非表示にする事が可能である。以下が戻るボタンと、アップボタンを実装する際の注意点となる。
				
				・アップボタンはアプリ内においてのみ、画面間のヒエラルキー関係に基づいた画面遷移を行います。アップボタンではアプリの外(通常はホーム画面）に遷移させることはありませ
					ん。
				・戻るボタンはシステムナビゲーションバーに表示されたボタンです。機種によっては画面外の物理ボタンである場合もあります。アプリに依存していません。
				・戻るボタンはユーザーが直近で遷移した画面の履歴（バックスタック）を遡るように遷移させます。
		
		・アップボタンの実装：ナビゲーションコンポーネントにはNavigationUIというUIライブラリが含まれます。ナビゲーションコントローラーによってアプリバーにアップボタンの挙動が実装されるので、
			自身で行う必要はありません。以下の手順でナビゲーションコントローラーを使ってアプリにアップボタンを追加できます。
							
							手順：
								1,MainActivity.ktを開いてください。onCreate()メソッドの中にナビゲーションコントローラーオブジェクトを見つけるため
									のコードを追加してください。
									val navController = this.findNavController(R.id.myNavHostFragment)
								
								2,さらにonCreate()メソッドの中に、ナビゲーションコントローラーとアプリバーをリンクさせるコードを追加してください。
									NavigationUI.setupActionBarWithNavController(this,navController)
									
								3,onCreate()メソッドの後に、ナビゲーションコントローラーのnavigateUp()を呼び出すために、
									onSupportNavigateUp()をオーバーライドしてください。
								
	[オプションメニュー]
		・手順
			1,Android Studioのプロジェクトパネルでresフォルダーの上で右クリックし、New > Android Resource Fileを選択してください。
			2,New Resource Fileダイアログで、ファイル名をoptions_menuとしてください。
			3,Resource typeにはMenuを選択し、OKをクリックしてください。
			4,作成されたレイアウトファイルを開き、「Menu Item」を追加する。メニューアイテムのid及びtitleを決定する。ここで追加する"id"はナビゲーショングラフに追加したFragment
				の"id"と一致させる必要がある。
			5,aboutメニューがタップされた時の挙動を実装する。
			
	[ナビゲーションドロワー]
		・ナビゲーションドロワーはMaterial Components for Androidライブラリの一部です。簡単にMaterialライブラリと呼ぶこともあります。MaterialライブラリはGoogleのマテリアルデザイン
			ガイドラインのパターンを実装する際に利用します。
			
	
	[フラグメント間のデータの引き渡し]
		
	Sage Args
		・あるフラグメントから別のフラグメントに引数を渡せるようにする必要があります。このやり取りでのバグを避け、型安全にするために、Safe Argsと呼ばれるGradleプラグインを使います。この
			プラグインはNavDirectionクラスを生成します。コードにこれらのクラスを追加していきます。データを渡す方法の一つとして、Bundleクラスのインスタンスを使う手法があります。
			Android Bundleはキーバリューストア(KVS)です。ディクショナリー、または連想配列としても知られるKVSは独自のキー(string)を使用して関連した値を取り出すデータ機構です。

		例えば、フラグメントAはbundleを作成し、キーと値のペアとして情報を保存します。それからBundleをフラグメントBに渡します。フラグメントBはキーを使ってBundleからキーに対応する値を取り出し
			ます。この技術は機能はしますが、コンパイルはできるけど動作時にエラーを起こす可能性があるコードを生み出します。以下が例
				
				・型不一致エラー
				・ミッシングキーエラー
				
		ユーザーがこれらのエラーに遭遇する前に、開発中にエラーをキャッチする必要があります。

		手順：
			1,Android Studioでプロジェクトレベルのbuild.gradleファイルを開いてください。androidx.navigation:navigation-safe-args-gradle-plugin:依存関係を追加し
				てください。
			2,アプリレベルのbuild.gradleファイルを開いてください。ファイルの最上部、他のプラグインのあとにapply plugin文をandroidx.navigation.safeargsプラグインと共に追
				加してください。これで、各フラグメントに対して、NavDirectionクラスが生成された。これはアプリ内のすべてのアクションからのナビゲーションを表す。	
				(view.findNavController().navigate(GameFragmentDirections.actionGa~)とアクセスできる)
			
		引数を追加する手順：
			1,res > navigationフォルダーからnavigation.xmlを開いてください。Designタブをクリックしてナビゲーショングラフを開いてください。プレビュー内のFragment(値を受け取る
				側)を選択してください。
			2,アトリビュートパネルのArgumentsセクションを展開してください。
			3,＋アイコンをクリックして引数を追加してください。名前と、Typerを選択してAddをクリックしてください。この状態でビルドしようとすると、コンパイルエラーが発生します。
			4,値を渡すフラグメントから、引数を渡す。　→ 							
			　　　　　　　　　　view.findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(numQuestions, questionIndex))
			5,値を受け取る側で、bundleから引数を抽出する。
			
		Intentを用いり、外部に共有することもできる。
		

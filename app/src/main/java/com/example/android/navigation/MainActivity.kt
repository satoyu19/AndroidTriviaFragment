/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //ドロワーレイアウトを表すメンバ変数
//    lateinit var →　変数の初期化をnull参照を返す危険性なしに遅らせることができます。
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        @Suppress() →　JavaのSuppressWarnings
        @Suppress("UNUSED_VARIABLE")    //使用しないパラメータでの警告を制御する。これは必要か？
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout

        //ナビゲーションコンローラーを利用してアプリにアップボタンを追加する
        val navController = this.findNavController(R.id.myNavHostFragment)
        //ナビゲーションコントローラーとアプリバーをリンクさせる
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        //ユーザーがナビゲーションドロワーを表示できるようにする
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    //このメソッドは、ユーザーがアクションバーからアプリケーションのアクティビティ階層内で上に移動することを選択したときに呼び出されます。
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)

        //ナビゲーション階層を上に移動しようとします。
//        return navController.navigateUp()

        //ナビゲーションドロワーの追加に伴い変更、ナビゲーションコントローラーとナビゲーションドロワーを両方実装する場合はこれ？
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}

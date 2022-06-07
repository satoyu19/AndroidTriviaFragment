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

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        binding.nextMatchButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }

        //bundleから引数を抽出
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
            //引数の抽出に成功
        Toast.makeText(context, "NumCorrect${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_SHORT).show()

        setHasOptionsMenu(true)
        return binding.root
    }

    //    Fragmentホストの標準オプションメニューの内容を初期化します。メニューには、メニューアイテムを配置する必要があります。このメソッドを呼び出すには
    //    まず setHasOptionsMenu を呼び出す必要があります。詳しくはActivity.onCreateOptionsMenuを参照してください。
    //オプションメニューを追加、メニューリソースファイルをインフレートしています。
    // 共有メニューの項目を動的に表示する
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        if(null == getShareIntent().resolveActivity(activity!!.packageManager)){
            // 解決しない場合はメニュー項目を非表示にする
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

//    インテントを作成するときには、インテントに実行させるアクションを指定する必要があります。
//    Android は、アクション ACTION_SEND を使用して、あるアクティビティから別のアクティビティにデータを送信します。
//    このとき、プロセス境界を越えることもできます。今回の場合、プロセスの境界をこえる
    private fun getShareIntent(): Intent{
        val args = GameWonFragmentArgs.fromBundle(arguments!!)
    //applyを利用した実装
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            //送信するデータに対して最も限定的な MIME タイプを指定する必要があります。
            // たとえば、書式なしテキストを共有するときには text/plain を使用する必要があります。
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        }
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.setType("text/Plain")
//            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
        return shareIntent
    }

    //getShareIntent()からIntentを取得し、共有するためにstartActivity()を呼び出します
    private fun shareSuccess(){
        startActivity(getShareIntent())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}

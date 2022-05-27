package com.seanutf.mediapreviewgrid

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seanutf.media.queryprovider.QueryMode
import com.seanutf.media.queryprovider.config.QueryConfig
import com.seanutf.media.queryprovider.data.ImgFormat

/**
 * 媒体库展示列表
 */
class MediaPreviewGridFragment : Fragment() {


    private lateinit var rvMediaList: RecyclerView
    private val listAdapter = MediaPreviewAdapter()
    private val viewModel: MediaPreviewViewModel by viewModels()
    var queryConfig: QueryConfig? = null
    private var clickMediaListener: MediaItemClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 确认容器 Activity 已实现该回调接口。否则，抛出异常
        try {
            clickMediaListener = context as? MediaItemClickListener?
        } catch (e: ClassCastException) {
//            throw ClassCastException(
//                context.toString()
//                        + " must implement MediaItemClickListener"
//            )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_media_preview_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initViewModel()
        loadMediaData()
    }

    private fun loadMediaData() {
        viewModel.loadMediaData()
    }

    private fun initView(view: View) {
        rvMediaList = view.findViewById(R.id.rvMediaList)
        rvMediaList.layoutManager = GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
        listAdapter.setMediaListener(clickMediaListener)
        rvMediaList.adapter = listAdapter
    }

    private fun initViewModel() {
        createConfig()
        queryConfig?.let { viewModel.setConfig(requireActivity().application, it) }

        viewModel.showLoading.observe(viewLifecycleOwner){
            //showLoading(it)
        }

        viewModel.uiShowState.observe(viewLifecycleOwner){
            when(it!!){
                UiShowState.LOADING -> {
                    //showLoading(true)
                }

                UiShowState.ERROR -> {
                    //showLoading(false)
                }

                UiShowState.SUCCESS -> {
                    //showLoading(false)
                    listAdapter.setList(viewModel.getMediaStoreList())
                    rvMediaList.scrollToPosition(0)
                }
            }
        }
    }

    private fun createConfig() {
        if (queryConfig == null) {
            queryConfig = QueryConfig()
            with(queryConfig ?: return) {
                mode = QueryMode.ALL
                imgQueryFormatArray = arrayOf(ImgFormat.IMG_JPG, ImgFormat.IMG_PNG, ImgFormat.IMG_AVIF)
            }
        }
    }

    companion object {
        const val TAG = "MediaPreviewGridFragment"

        @JvmStatic
        fun newInstance() =
            MediaPreviewGridFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
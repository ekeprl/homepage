package egovframework.let.board.service.impl;

import egovframework.let.board.service.BoardService;
import egovframework.let.board.service.BoardVO;
import egovframework.let.temp2.service.Temp2Service;
import egovframework.let.temp2.service.Temp2VO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service("boardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService {

       
    @Resource(name="boardMapper")
    private BoardMapper boardMapper;
    
    @Resource(name = "egovBoardIdGnrService") /*이 리소스를 통하여context-idgen.xml을 연결해 값을 가져온다.*/
    private EgovIdGnrService idgenService;
    
    //게시물 목록 가져오기
    public List<EgovMap> selectBoardList(BoardVO vo) throws Exception{
    	return boardMapper.selectBoardList(vo);
    }
    
    //게시물 목록 수
    public int selectBoardListCnt(BoardVO vo) throws Exception {
    	return boardMapper.selectBoardListCnt(vo);
    }
    //게시물 등록
    @Override
    public String insertBoard(BoardVO vo) throws Exception {
    		String id = idgenService.getNextStringId();
    		vo.setBoardId(id);
    		boardMapper.insertBoard(vo);
    		return id;
    }
    
    //게시물 상세정보
    @Override
    public BoardVO selectBoard(BoardVO vo) throws Exception {
    	//조회수 업  (impl에있는 이유 :트랜잭션 처리를 위해) 중간에 오류가나면 롤백이 실행된다.
    	boardMapper.updateViewCnt(vo);
    	
    	return boardMapper.selectBoard(vo); //세개가 끝까지 진행되야 커밋이 실행된다. 하나라도 오류나면 롤백
    }
    
    //게시물 수정하기
    @Override
    public void updateBoard(BoardVO vo) throws Exception {
    	boardMapper.updateBoard(vo);
    }
    
    //게시물 삭제하기
    
    @Override
    public void deleteBoard(BoardVO vo) throws Exception {
    	boardMapper.deleteBoard(vo);
    }
    
    
    
}

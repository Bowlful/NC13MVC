package controller;

import model.ReplyDTO;

import java.util.ArrayList;

public class ReplyController {

    private ArrayList<ReplyDTO> list;
    private int nextId;

    public ReplyController() {
        list = new ArrayList<>();
        nextId = 1;
    }

    public void insert(ReplyDTO replyDTO) {
        replyDTO.setId(nextId++);
        list.add(replyDTO);
    }

    public ReplyDTO selectOne(int id) {
        ReplyDTO temp = new ReplyDTO();
        temp.setId(id);
        return list.get(list.indexOf(temp));
    }

    public ArrayList<ReplyDTO> selectListByBoardId(int id) {
        ArrayList<ReplyDTO> temp = new ArrayList<>();
        for(ReplyDTO r : list) {
            if(r.getBoardId() == id) {
                temp.add(r);
            }
        }
        return temp;
    }

    public ArrayList<ReplyDTO> selectListByBoardIdAndWriterId(int boardId, int writerId) {
        ArrayList<ReplyDTO> temp = new ArrayList<>();
        for(ReplyDTO r : list) {
            if(r.getBoardId() == boardId && r.getWriterId() == writerId) {
                temp.add(r);
            }
        }
        return temp;
    }


    public ArrayList<ReplyDTO> selectAll() {
        return list;
    }

    public void update(ReplyDTO replyDTO) {
        list.set(list.indexOf(replyDTO), replyDTO);
    }

    public void delete(int id) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setId(id);
        list.remove(replyDTO);
    }

}

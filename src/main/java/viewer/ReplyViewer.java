package viewer;

import controller.ReplyController;
import controller.UserController;
import lombok.Setter;
import model.ReplyDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class ReplyViewer {

    @Setter
    private ReplyController replyController;
    @Setter
    private UserController userController;
    @Setter
    private Scanner scanner;

    private void printReplyListByBoardIdAndWriterId(int boardId, int writerId) {
        ArrayList<ReplyDTO> list = replyController.selectListByBoardIdAndWriterId(boardId, writerId);
        for (ReplyDTO r : list) {
            System.out.printf("%d. %s : %s\n", r.getId(), userController.selectNicknameById(r.getWriterId()), r.getContent());
        }
    }

    public void showReplyByBoardId(int id) {
        ArrayList<ReplyDTO> list = replyController.selectListByBoardId(id);
        for(ReplyDTO r : list) {
            System.out.printf("%d. %s : %s\n", r.getId(), userController.selectNicknameById(r.getWriterId()), r.getContent());
        }
        System.out.println("=====================================");
    }

    public void insertReply(int boardId, int writerId) {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setBoardId(boardId);
        replyDTO.setWriterId(writerId);

        String message = "댓글을 입력해주세요";
        replyDTO.setContent(ScannerUtil.nextLine(scanner, message));

        replyController.insert(replyDTO);
    }

    public void updateReply(int boardId, int writerId) {
        printReplyListByBoardIdAndWriterId(boardId, writerId);

        String message = "수정할 댓글 번호 입력해주세요";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        ReplyDTO replyDTO = replyController.selectOne(userChoice);
        message = "댓글을 다시 입력해주세요";
        replyDTO.setContent(ScannerUtil.nextLine(scanner, message));

        replyController.update(replyDTO);
    }

    public void deleteReply(int boardId, int writerId) {
        printReplyListByBoardIdAndWriterId(boardId, writerId);
        String message = "삭제할 댓글 번호 입력해주세요";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        ReplyDTO replyDTO = replyController.selectOne(userChoice);

        message = "정말 삭제하시겠습니까? Y/N";
        String answer = ScannerUtil.nextLine(scanner, message);
        if (answer.equalsIgnoreCase("Y")) {
            replyController.delete(replyDTO.getId());
        }
    }
}

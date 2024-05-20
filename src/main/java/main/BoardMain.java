package main;

import controller.BoardController;
import controller.ReplyController;
import controller.UserController;
import viewer.BoardViewer;
import viewer.ReplyViewer;
import viewer.UserViewer;

import java.util.Scanner;

public class BoardMain {
    public static void main(String[] args) {
        // 입력에 사용할 Scanner 클래스 객체
        Scanner scanner = new Scanner(System.in);

        // 각종 컨트롤러 클래스 객체
        UserController userController = new UserController();
        BoardController boardController = new BoardController();
        ReplyController replyController = new ReplyController();

        // 각종 뷰어 클래스 객체
        UserViewer userViewer = new UserViewer();
        BoardViewer boardViewer = new BoardViewer();
        ReplyViewer replyViewer = new ReplyViewer();

        // setter를 사용한 의존성 주입
        userViewer.setScanner(scanner);
        userViewer.setUserController(userController);
        userViewer.setBoardViewer(boardViewer);

        boardViewer.setScanner(scanner);
        boardViewer.setBoardController(boardController);
        boardViewer.setUserController(userController);
        boardViewer.setReplyViewer(replyViewer);

        replyViewer.setScanner(scanner);
        replyViewer.setReplyController(replyController);
        replyViewer.setUserController(userController);

        userViewer.showIndex();
    }
}

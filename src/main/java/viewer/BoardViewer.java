package viewer;

import controller.BoardController;
import controller.UserController;
import lombok.Setter;
import model.BoardDTO;
import model.UserDTO;
import util.ScannerUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardViewer {

    @Setter
    private BoardController boardController;
    @Setter
    private UserController userController;
    @Setter
    private ReplyViewer replyViewer;
    @Setter
    private Scanner scanner;
    @Setter
    private UserDTO logIn;

    public void showMenu() {
        String message = "1. 글 작성하기 2. 글 목록 보기 3. 뒤로가기";
        while (true) {

            int userChoice = ScannerUtil.nextInt(scanner, message);

            if(userChoice == 1) {
                insert();
            } else if (userChoice == 2) {
                printList();
            } else if (userChoice == 3) {
                System.out.println("메인 화면으로 돌아갑니다.");
                break;
            }

        }
    }

    private void insert() {
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setWriterId(logIn.getId());

        String message = "글의 제목을 입력해주세요.";
        boardDTO.setTitle(ScannerUtil.nextLine(scanner, message));

        message = "글의 내용을 입력해주세요";
        boardDTO.setContent(ScannerUtil.nextLine(scanner, message));

        boardController.insert(boardDTO);
    }

    private void printList() {
        ArrayList<BoardDTO> list = boardController.selectAll();
        for(BoardDTO b : list) {
            System.out.printf("%d. %s - %s\n", b.getId(), b.getTitle(), userController.selectNicknameById(b.getWriterId()));
        }

        String message = "상세보기할 글의 번호나 뒤로 가실려면 0을 입력해주세요;";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        while (!boardController.validateInput(userChoice)) {
            System.out.println("잘못 입력하셨습니다.");
            userChoice = ScannerUtil.nextInt(scanner, message);
        }

        if(userChoice != 0) {
            printOne(userChoice);
        }

    }

    private void printOne(int id) {
        BoardDTO boardDTO = boardController.selectOne(id);
        System.out.println("=====================================");
        System.out.println("글 번호 : " + boardDTO.getId());
        System.out.println("글 작성자 : " + userController.selectNicknameById(boardDTO.getId()));
        System.out.println("-------------------------------------");
        System.out.println(boardDTO.getContent());
        System.out.println("=====================================");

        replyViewer.showReplyByBoardId(id);

        if(logIn.getId() == boardDTO.getWriterId()) {
            String message = "1. 수정 2. 삭제 3. 댓글 입력 4. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(scanner, message);
            if(userChoice == 1) {
                update(boardDTO.getId());
            } else if (userChoice == 2) {
                delete(boardDTO.getId());
            } else if (userChoice == 3) {
                replyViewer.insertReply(id, logIn.getId());
                printOne(id);
            } else if (userChoice == 4) {
                printList();
            }
        } else {
            String message = "1. 댓글 입력 2. 뒤로가기";
            int userChoice = ScannerUtil.nextInt(scanner, message, 1, 2);
            if(userChoice == 1) {
                replyViewer.insertReply(id, logIn.getId());
                printOne(id);
            } else {
                printList();
            }
        }
    }

    private void update(int id) {

        String message = "1. 게시글 수정 2. 댓글 수정";
        int userChoice = ScannerUtil.nextInt(scanner, message);

        if(userChoice == 1) {
            BoardDTO b = boardController.selectOne(id);

            message = "글의 제목을 다시 입력해주세요.";
            b.setTitle(ScannerUtil.nextLine(scanner, message));

            message = "글의 내용을 다시 입력해주세요";
            b.setContent(ScannerUtil.nextLine(scanner, message));

            boardController.update(b);
            printOne(id);
        } else if(userChoice == 2) {
            replyViewer.updateReply(id, logIn.getId());
            printOne(id);
        }

    }

    private void delete(int id) {
        String message = "1. 게시글 삭제 2. 댓글 삭제";
        int userChoice = ScannerUtil.nextInt(scanner, message);
        if (userChoice == 1) {
            String answer = ScannerUtil.nextLine(scanner, "정말로 삭제하시겠습니까? Y/N");
            if (answer.equalsIgnoreCase("Y")) {
                boardController.delete(id);
                printList();
            } else {
                printOne(id);
            }
        } else if (userChoice == 2) {
            replyViewer.deleteReply(id, logIn.getId());
            printOne(id);
        }
    }
}
